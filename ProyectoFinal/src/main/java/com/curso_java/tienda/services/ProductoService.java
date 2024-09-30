package com.curso_java.tienda.services;

import com.curso_java.tienda.dtos.ProductoDTO;
import com.curso_java.tienda.entities.Producto;
import com.curso_java.tienda.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Servicio para gestionar productos.
 */
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    /**
     * Crea o actualiza un producto.
     *
     * @param producto El producto a crear o actualizar.
     * @return Un Mono que emite el DTO del producto creado o un error si el ID del producto ya existe.
     */
    public Mono<ProductoDTO> saveProducto(Producto producto) {
        var fechaCreacion = LocalDateTime.now();
        producto.setCreatedAt(fechaCreacion);
        producto.setUpdatedAt(fechaCreacion);

        return productoRepository.existsById(producto.getId())
                .flatMap(existe -> {
                    if (existe) {
                        return Mono.error(new IllegalArgumentException("El ID del producto ya existe"));
                    } else {
                        return r2dbcEntityTemplate.insert(Producto.class).using(producto)
                                .map(productoGuardado -> toProductoDTO(productoGuardado));
                    }
                });
    }

    /**
     * Obtiene todos los productos.
     *
     * @return Un Flux que emite los DTOs de todos los productos.
     */
    public Flux<ProductoDTO> getAllProductos() {
        return productoRepository.findAll()
                .flatMap(producto -> Flux.just(toProductoDTO(producto)));
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param id El ID del producto.
     * @return Un Mono que emite el DTO del producto encontrado o vacío si no se encuentra.
     */
    public Mono<ProductoDTO> getProductoById(String id) {
        return productoRepository.findById(id)
                .map(producto -> toProductoDTO(producto));
    }

    /**
     * Actualiza un producto por su ID.
     *
     * @param id El ID del producto a actualizar.
     * @param producto Los datos del producto a actualizar.
     * @return Un Mono que emite el DTO del producto actualizado o vacío si no se encuentra.
     */
    public Mono<ProductoDTO> updateProducto(String id, Producto producto) {
        return productoRepository.findById(id)
                .flatMap(p -> {
                    p.setNombre(producto.getNombre());
                    p.setPrecio(producto.getPrecio());
                    p.setDescripcion(producto.getDescripcion());
                    p.setImagenUrl(producto.getImagenUrl());
                    p.setStock(producto.getStock());
                    p.setUpdatedAt(LocalDateTime.now());
                    return productoRepository.save(p);
                }).map(productoActualizado -> toProductoDTO(productoActualizado));
    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id El ID del producto a eliminar.
     * @return Un Mono que emite el DTO del producto eliminado o vacío si no se encuentra.
     */
    public Mono<ProductoDTO> deleteProducto(String id) {
        return productoRepository.findById(id)
                .flatMap(producto -> productoRepository.delete(producto)
                        .then(Mono.just(toProductoDTO(producto))))
                .switchIfEmpty(Mono.empty());
    }

    /**
     * Convierte un Producto a ProductoDTO.
     *
     * @param producto El producto a convertir.
     * @return El DTO del producto.
     */
    private ProductoDTO toProductoDTO(Producto producto) {
        return new ProductoDTO(producto.getId(), producto.getNombre(), producto.getPrecio(), producto.getDescripcion(), producto.getStock());
    }
}