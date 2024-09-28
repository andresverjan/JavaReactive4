package com.programacion.reactiva.trabajo_final.service;

import com.programacion.reactiva.trabajo_final.model.Carrito;
import com.programacion.reactiva.trabajo_final.model.CarritoProducto;
import com.programacion.reactiva.trabajo_final.model.Producto;
import com.programacion.reactiva.trabajo_final.model.dto.CarritoComprasDTO;
import com.programacion.reactiva.trabajo_final.model.dto.CarritoProductoDTO;
import com.programacion.reactiva.trabajo_final.model.dto.ProductoDTO;
import com.programacion.reactiva.trabajo_final.repository.CarritoProductoRepository;
import com.programacion.reactiva.trabajo_final.repository.CarritoRepository;
import com.programacion.reactiva.trabajo_final.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CarritoService {
    private static final Logger logger = LoggerFactory.getLogger(CarritoService.class);
    private final CarritoProductoRepository carritoProductoRepository;
    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;
    private final ProductoService productoService;

    public CarritoService(CarritoProductoRepository carritoProductoRepository, CarritoRepository carritoRepository,
                          ProductoRepository productoRepository, ProductoService productoService) {
        this.carritoProductoRepository = carritoProductoRepository;
        this.carritoRepository = carritoRepository;
        this.productoRepository = productoRepository;
        this.productoService = productoService;
    }

    public Mono<CarritoProductoDTO> agregarProductoAlCarrito(CarritoProducto carritoProducto) {
        return validarCarritoCompra((long) carritoProducto.getCarritoId()).flatMap(agregarCarrito -> {
            carritoProducto.setCarritoId(agregarCarrito.getId());
            return consultarStockProducto((long) carritoProducto.getProductoId(), carritoProducto.getCantidad())
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
                .switchIfEmpty(Mono.error(new Exception("Carrito no encontrado")));
    }

    public Mono<Producto> consultarStockProducto(Long productId, int cantidad) {
        return productoRepository.findById(productId)
                .switchIfEmpty(Mono.error(new Exception("Producto no encontrado")))
                .flatMap(producto -> {
                    if (producto.getStock() < cantidad) {
                        logger.error("No hay stock suficiente para el producto: {}", producto);
                        return Mono.error(new Exception("No hay stock suficiente para el producto seleccionado. Stock actual: " + producto.getStock()));
                    }
                    return Mono.just(producto);
                });
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
                .flatMap(this::obtenerCarritoProductoDTO)
                .collectList()
                .map(carritoProductosDTO -> CarritoComprasDTO.builder()
                        .carritoId(id)
                        .items(carritoProductosDTO)
                        .build())
                .flux();
    }


    public Mono<CarritoProductoDTO> actualizarCantidadProducto(Long carritoId, Long productoId, int cantidad) {
        return carritoProductoRepository.findByCarritoIdAndProductoId(carritoId, productoId)
                .flatMap(actualizarCantidad -> {
                    if (cantidad < actualizarCantidad.getCantidad()) {
                        logger.info("Disminuyendo stock del producto: {}", actualizarCantidad);
                        return consultarStockProducto(productoId, -cantidad)
                                .flatMap(disminuirStock -> {
                                    actualizarCantidad.setCantidad(actualizarCantidad.getCantidad() - cantidad);
                                    return guardarProductoCarrito(actualizarCantidad, disminuirStock, -cantidad);
                                });
                    } else {
                        logger.info("Aumentando stock del producto: {}", actualizarCantidad);
                        return consultarStockProducto(productoId, cantidad)
                                .flatMap(aumentarStock -> {
                                    actualizarCantidad.setCantidad(actualizarCantidad.getCantidad() + cantidad);
                                    return guardarProductoCarrito(actualizarCantidad, aumentarStock, cantidad);
                                });
                    }
                });
    }


    private Mono<CarritoProductoDTO> obtenerCarritoProductoDTO(CarritoProducto carritoProducto) {
        return productoRepository.findById((long) carritoProducto.getProductoId())
                .map(producto ->
                        CarritoProductoDTO.builder()
                                .producto(ProductoDTO.builder()
                                        .id(producto.getId())
                                        .name(producto.getName())
                                        .price(producto.getPrice())
                                        .description(producto.getDescription())
                                        .imageUrl(producto.getImageUrl())
                                        .build())
                                .cantidad(carritoProducto.getCantidad())
                                .build());
    }

    public Mono<Void> eliminarProductoCarrito(Long carritoId, Long productoId) {
        return carritoProductoRepository.deleteByCarritoIdAndProductoId(carritoId, productoId);
    }

    public Mono<Void> eliminarCarrito(Long carritoId) {
        return carritoProductoRepository.deleteByCarritoId(carritoId)
                .then(carritoRepository.deleteById(carritoId));
    }


}
