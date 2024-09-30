package com.programacion.reactiva.trabajo_final.service;

import com.programacion.reactiva.trabajo_final.exceptions.BusinessException;
import com.programacion.reactiva.trabajo_final.model.*;
import com.programacion.reactiva.trabajo_final.model.dto.*;
import com.programacion.reactiva.trabajo_final.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CarritoService {
    private static final Logger logger = LoggerFactory.getLogger(CarritoService.class);
    private final CarritoProductoRepository carritoProductoRepository;
    private final CarritoRepository carritoRepository;
    private final ProductoService productoService;
    private final OrdenVentaService ordenVentaService;

    public CarritoService(CarritoProductoRepository carritoProductoRepository,
                          CarritoRepository carritoRepository,
                          ProductoService productoService,
                          OrdenVentaService ordenVentaService) {
        this.carritoProductoRepository = carritoProductoRepository;
        this.carritoRepository = carritoRepository;
        this.productoService = productoService;
        this.ordenVentaService = ordenVentaService;
    }

    public Mono<CarritoProductoDTO> agregarProductoAlCarrito(CarritoProducto carritoProducto) {
        return validarCarritoCompra((long) carritoProducto.getCarritoId()).flatMap(agregarCarrito -> {
            carritoProducto.setCarritoId(agregarCarrito.getId());
            return productoService.consultarStockProducto((long) carritoProducto.getProductoId(), carritoProducto.getCantidad())
                    .flatMap(producto -> carritoProductoRepository
                            .findByCarritoIdAndProductoId((long) carritoProducto.getCarritoId(), (long) carritoProducto.getProductoId())
                            .flatMap(carritoProductoExistente -> {
                                carritoProductoExistente.setCantidad(carritoProductoExistente.getCantidad() + carritoProducto.getCantidad());
                                return guardarProductoCarrito(carritoProductoExistente, producto, carritoProducto.getCantidad());
                            })
                            .switchIfEmpty(guardarProductoCarrito(carritoProducto, producto, carritoProducto.getCantidad()))
                    );
        });
    }

    public Mono<Carrito> validarCarritoCompra(Long carritoId) {
        return (carritoId == 0 ? Mono.defer(() -> carritoRepository.save(new Carrito())) : carritoRepository.findById(carritoId))
                .switchIfEmpty(Mono.error(new BusinessException(404, "Carrito no encontrado")));
    }

    public Mono<CarritoProductoDTO> guardarProductoCarrito(CarritoProducto carritoProducto, Producto producto, int cantidad) {
        return carritoProductoRepository.save(carritoProducto)
                .flatMap(guardarProducto -> {
                    logger.info("Producto guardado en carrito: {}", guardarProducto);
                    return productoService.actualizarStock((long) producto.getId(),
                                    producto.getStock() - cantidad)
                            .then(Mono.just(
                                    CarritoProductoDTO.builder()
                                            .carritoId((long) carritoProducto.getCarritoId())
                                            .producto(ProductoDTO.builder()
                                                    .id(producto.getId())
                                                    .name(producto.getName())
                                                    .price(producto.getPrice())
                                                    .description(producto.getDescription())
                                                    .imageUrl(producto.getImageUrl())
                                                    .build())
                                            .cantidad(guardarProducto.getCantidad())
                                            .build()));
                });
    }

    public Flux<CarritoComprasDTO> obtenerCarrito(Long id) {
        return carritoProductoRepository.findByCarritoId(id)
                .collectList()
                .flatMapMany(validarCarrito -> {
                    if (validarCarrito.isEmpty()) {
                        return Flux.error(new BusinessException(400, "No hay productos en el carrito"));
                    }
                    List<ProductoCantidadDTO> productos = validarCarrito.stream()
                            .map(carritoProducto -> ProductoCantidadDTO.builder()
                                    .productoId(carritoProducto.getProductoId())
                                    .cantidad(carritoProducto.getCantidad())
                                    .build())
                            .collect(Collectors.toList());
                    return productoService.mapearItemsVentas(productos)
                            .flux();
                });
    }


    public Mono<CarritoProductoDTO> actualizarCantidadProducto(Long carritoId, Long productoId, int cantidad) {
        return carritoProductoRepository.findByCarritoIdAndProductoId(carritoId, productoId)
                .flatMap(actualizarCantidad -> {
                    if (cantidad < actualizarCantidad.getCantidad()) {
                        logger.info("Disminuyendo stock del producto: {}", actualizarCantidad);
                        return productoService.consultarStockProducto(productoId, -cantidad)
                                .flatMap(disminuirStock -> {
                                    actualizarCantidad.setCantidad(actualizarCantidad.getCantidad() - cantidad);
                                    return guardarProductoCarrito(actualizarCantidad, disminuirStock, -cantidad);
                                });
                    } else {
                        logger.info("Aumentando stock del producto: {}", actualizarCantidad);
                        return productoService.consultarStockProducto(productoId, cantidad)
                                .flatMap(aumentarStock -> {
                                    actualizarCantidad.setCantidad(actualizarCantidad.getCantidad() + cantidad);
                                    return guardarProductoCarrito(actualizarCantidad, aumentarStock, cantidad);
                                });
                    }
                });
    }


    public Mono<Void> eliminarProductoCarrito(Long carritoId, Long productoId) {
        return carritoProductoRepository.deleteByCarritoIdAndProductoId(carritoId, productoId);
    }

    public Mono<Void> eliminarCarrito(Long carritoId) {
        return carritoProductoRepository.deleteByCarritoId(carritoId)
                .then(carritoRepository.deleteById(carritoId));
    }

    public Mono<ValorTotalDTO> obtenerValorTotalCarrito(Long carritoId, Double envio) {
        return carritoProductoRepository.findByCarritoId(carritoId)
                .collectList()
                .flatMap(carritoProductos -> {
                    if (carritoProductos.isEmpty()) {
                        return Mono.error(new BusinessException(400,"No hay productos en el carrito, no se puede calcular el total"));
                    }
                    List<ProductoCantidadDTO> productos = carritoProductos.stream()
                            .map(carritoProducto -> ProductoCantidadDTO.builder()
                                    .productoId(carritoProducto.getProductoId())
                                    .cantidad(carritoProducto.getCantidad())
                                    .build())
                            .toList();
                    return productoService.obtenerValorTotal(productos, envio);
                });
    }


    public Mono<VentaDTO> registrarOrdenVenta(Long carritoId, Double envio) {
        return carritoProductoRepository.findByCarritoId(carritoId)
                .collectList()
                .flatMap(carritoProductos -> {
                    if (carritoProductos.isEmpty()) {
                        return Mono.error(new BusinessException(404,"No hay productos en el carrito, no se puede realizar la compra"));
                    }
                    List<ProductoCantidadDTO> productos = carritoProductos.stream()
                            .map(carritoProducto -> ProductoCantidadDTO.builder()
                                    .productoId(carritoProducto.getProductoId())
                                    .cantidad(carritoProducto.getCantidad())
                                    .build())
                            .toList();
                    return productoService.obtenerValorTotal(productos, envio)
                            .flatMap(valorTotal ->
                                    ordenVentaService.crearOrdenVenta(valorTotal.getValorTotal())
                                            .flatMap(ordenVentaGuardada ->
                                                    ordenVentaService.crearOrdenVentaProducto(productos, ordenVentaGuardada.getId())
                                                            .collectList()
                                                            .flatMap(items -> productoService.mapearItemsVentas(productos))
                                                            .flatMap(carritoComprasDTO -> eliminarCarrito(carritoId)
                                                                    .thenReturn(ordenVentaService.ventaResponse(ordenVentaGuardada, carritoComprasDTO, valorTotal))
                                                            )
                                            )
                            );
                });
    }
}
