package com.example.demo.controler;

import com.example.demo.model.Persona;
import com.example.demo.model.Producto;
import com.example.demo.service.ProductoService;
import jakarta.validation.Valid;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;


    @GetMapping("/{id}")
    public Mono<Producto> getProductoById(@PathVariable Long id) {
        return service.getProductoById(id);
    }

    @GetMapping
    public Flux<Producto> getAllProductos() {
        return service.getAllProductos();
    }

//    @PostMapping("/create")
//    public Mono<Persona> createPersona(@Valid @RequestBody Persona persona) {
//        return service.createPersona(persona);
//    }
    @PostMapping("/create")
    public Mono<ResponseEntity<Producto>> createProducto(@Valid @RequestBody Producto producto) {
        producto.setCreatedAt(LocalDateTime.now());
        producto.setUpdatedAt(LocalDateTime.now());
        return service.createProducto(producto)
                .map(savedProducto -> ResponseEntity.ok(savedProducto))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PutMapping("/update/{id}")
    public Mono<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
        producto.setUpdatedAt(LocalDateTime.now());
        return service.getProductoById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Producto no encontrado con el ID: " + id)))
                .flatMap(existingProducto -> service.updateProducto(id, producto));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteProducto(@PathVariable Long id) {
        return service.getProductoById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Producto no encontrado con el ID: " + id)))
                .flatMap(producto -> service.deleteProducto(id));
    }
}