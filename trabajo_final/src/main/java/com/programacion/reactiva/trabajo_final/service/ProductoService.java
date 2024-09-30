package com.programacion.reactiva.trabajo_final.service;

import com.programacion.reactiva.trabajo_final.exceptions.BusinessException;
import com.programacion.reactiva.trabajo_final.model.Producto;
import com.programacion.reactiva.trabajo_final.model.dto.*;
import com.programacion.reactiva.trabajo_final.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class ProductoService {
    private static final Logger logger = LoggerFactory.getLogger(CarritoService.class);

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Flux<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Mono<Producto> crearProducto(Producto producto) {
        return productoRepository.save(producto)
                .doOnNext(product -> logger.info("Producto creado: " + producto));
    }


    public Mono<Producto> editarProducto(Long id, Producto producto) {
        return productoRepository.findById(id)
                .flatMap(editarProducto -> {
                    editarProducto.setName(producto.getName());
                    editarProducto.setPrice(producto.getPrice());
                    editarProducto.setDescription(producto.getDescription());
                    editarProducto.setImageUrl(producto.getImageUrl());
                    editarProducto.setStock(producto.getStock());
                    return productoRepository.save(editarProducto);
                });
    }

    public Mono<Producto> consultarStockProducto(Long productId, int cantidad) {
        return productoRepository.findById(productId)
                .switchIfEmpty(Mono.error(new BusinessException(404,"Producto no encontrado")))
                .flatMap(producto -> {
                    if (producto.getStock() < cantidad) {
                        logger.error("No hay stock suficiente para el producto: {}", producto);
                        return Mono.error(new BusinessException(400, "No hay stock suficiente para el producto seleccionado. Stock actual: " + producto.getStock()));
                    }
                    return Mono.just(producto);
                });
    }

    public Mono<Producto> buscarProducto(Long id){
        return productoRepository.findById(id);
    }

    public Flux<Producto> buscarProductoPorNombre(String nombre){
        return productoRepository.findByNameContaining(nombre);
    }

    public Mono<Void> eliminarProducto(Long id){
        return productoRepository.deleteById(id);
    }

    public Mono<Producto> actualizarStock(Long id, int cantidad){
        return productoRepository.findById(id)
                .flatMap(producto -> {
                   logger.info("Actualizando stock del producto: {} " , producto.getName());
                    producto.setStock(cantidad);
                    return productoRepository.save(producto);
                });
    }

    public Mono<ValorTotalDTO> obtenerValorTotal(List<ProductoCantidadDTO> productos, Double envio) {
        return Flux.fromIterable(productos)
                .flatMap(producto -> productoRepository.findById((long)producto.getProductoId())
                        .map(p -> p.getPrice() * producto.getCantidad())
                )
                .reduce(0.0, Double::sum)
                .map(total -> {
                    Double impuestos = total * 0.19;
                    return   ValorTotalDTO.builder()
                            .impuestos(impuestos)
                            .costoEnvio(envio)
                            .subTotal(total)
                            .valorTotal(total + impuestos + envio).build();
                });
    }

    public Mono<CarritoProductoDTO> obtenerCarritoProductoDTO(ProductoCantidadDTO carritoProducto) {
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

    public Mono<CarritoComprasDTO> mapearItemsVentas(List<ProductoCantidadDTO> productos) {
        return Flux.fromIterable(productos)
                .flatMap(this::obtenerCarritoProductoDTO)
                .collectList()
                .map(carritoProductosDTO ->
                        CarritoComprasDTO.builder()
                                .items(carritoProductosDTO)
                                .build());
    }

    public Mono<OrdenCompraProductoDTO> obtenerCompraProductoDTO(ProductoCantidadDTO productoCantidadDTO) {
        return productoRepository.findById((long) productoCantidadDTO.getProductoId())
                .map(producto ->
                        OrdenCompraProductoDTO.builder()
                                .producto(ProductoDTO.builder()
                                        .id(producto.getId())
                                        .name(producto.getName())
                                        .price(producto.getPrice())
                                        .description(producto.getDescription())
                                        .imageUrl(producto.getImageUrl())
                                        .build())
                                .cantidad(productoCantidadDTO.getCantidad())
                                .build());

    }

    public Mono<OrdenCompraDTO> mapearItemsCompras(List<ProductoCantidadDTO> productos) {
        return Flux.fromIterable(productos)
                .flatMap(this::obtenerCompraProductoDTO)
                .collectList()
                .map(compraProductosDTO ->
                        OrdenCompraDTO.builder()
                                .items(compraProductosDTO)
                                .build());
    }
}
