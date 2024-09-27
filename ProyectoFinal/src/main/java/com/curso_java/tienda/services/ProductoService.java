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

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    // Crear o actualizar un producto
    public Mono<ProductoDTO> saveProducto(Producto producto) {
        var fechaCreacion= LocalDateTime.now();
        producto.setCreatedAt(fechaCreacion);
        producto.setUpdatedAt(fechaCreacion);

        return productoRepository.existsById(producto.getId())
                .flatMap(existe -> {
                    if (existe) {
                        return Mono.error(new IllegalArgumentException("El ID del producto ya existe"));
                    } else {
                        return r2dbcEntityTemplate.insert(Producto.class).using(producto).map(productoGuardado -> toProductoDTO(productoGuardado)); // Devuelve el DTO del usuario guardado // Realiza la inserción
                    }
                });
    }

    // Obtener todos los productos
    public Flux<ProductoDTO> getAllProductos() {
        return productoRepository.findAll().flatMap(producto -> Flux.just(toProductoDTO(producto))); // Devuelve un Flux de DTOs de productos);
    }

    // Obtener un producto por ID
    public Mono<ProductoDTO> getProductoById(String id) {
        return productoRepository.findById(id).map(producto -> toProductoDTO(producto)); // Devuelve el DTO del producto encontrado
    }

    // Actualizar un producto
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
                }).map(productoActualizado -> toProductoDTO(productoActualizado)); // Devuelve el DTO del producto actualizado
    }

    // Eliminar un producto
    public Mono<ProductoDTO> deleteProducto(String id) {
        return productoRepository.findById(id)
                .flatMap(
                        producto -> productoRepository.delete(producto)
                        .then(Mono.just(toProductoDTO(producto)))
                )// Devuelve el DTO del producto eliminado
                .switchIfEmpty(Mono.empty()); // No devuelve nada si no se encuentra el producto
    }

    // Convertir un Producto a ProductoDTO
    private ProductoDTO toProductoDTO(Producto producto) {
        return new ProductoDTO(producto.getId(), producto.getNombre(), producto.getPrecio(), producto.getDescripcion(), producto.getStock());
    }
}

