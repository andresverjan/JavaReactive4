package com.curso_java.tienda.services;

import com.curso_java.tienda.entities.Producto;
import com.curso_java.tienda.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Crear o actualizar un producto
    public Mono<Producto> saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Obtener todos los productos
    public Flux<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    // Obtener un producto por ID
    public Mono<Producto> getProductoById(String id) {
        return productoRepository.findById(id);
    }

    // Eliminar un producto
    public Mono<Void> deleteProducto(String id) {
        return productoRepository.deleteById(id);
    }
}

