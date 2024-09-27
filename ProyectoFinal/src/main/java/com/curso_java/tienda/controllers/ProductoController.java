package com.curso_java.tienda.controllers;

import com.curso_java.tienda.entities.Producto;
import com.curso_java.tienda.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Producto> createProducto(@RequestBody Producto producto) {
        return productoService.saveProducto(producto);
    }

    @GetMapping
    public Flux<Producto> getAllProductos() {
        return productoService.getAllProductos();
    }

    @GetMapping("/{id}")
    public Mono<Producto> getProductoById(@PathVariable String id) {
        return productoService.getProductoById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProducto(@PathVariable String id) {
        return productoService.deleteProducto(id);
    }
}

