package com.example.demo.service;

import com.example.demo.model.Persona;
import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;


    public Mono<Producto> getProductoById(Long id) {
        return repository.findById(id);
    }

    public Flux<Producto> getAllProductos() {
        return repository.findAll();
    }

//    public Mono<Persona> createPersona(Persona persona) {
//        return repository.save(persona);
//    }

    public  Mono<Producto> createProducto(Producto producto) {
        return repository.save(producto);
    }

    public Mono<Producto> updateProducto(Long id, Producto producto) {
        return repository.findById(id)
                .flatMap(productoActualizar -> {
                    productoActualizar.setName(producto.getName());
                    productoActualizar.setPrice(producto.getPrice());
                    productoActualizar.setDescription(producto.getDescription());
                    productoActualizar.setImageUrl(producto.getImageUrl());
                    productoActualizar.setStock(producto.getStock());
                    productoActualizar.setUpdatedAt(producto.getUpdatedAt());
                    return repository.save(productoActualizar);
                });
    }

    public Mono<Void> deleteProducto(Long id) {
        return repository.deleteById(id);
    }
}