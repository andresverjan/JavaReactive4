package com.example.EntregaFinal.controllers;

import com.example.EntregaFinal.model.Producto;
import com.example.EntregaFinal.service.ProductoService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Obtener la lista de todos los productos
    @GetMapping
    public Flux<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    // Obtener un producto por su ID
    @GetMapping("/{id}")
    public Mono<Producto> obtenerProductoPorId(@PathVariable Long id) {
        return productoService.buscarProductoPorId(id);
    }

    // Crear un nuevo producto
    @PostMapping
    public Mono<Producto> registrarProducto(@RequestBody Producto producto) {
        return productoService.crearProducto(producto);
    }

    // Actualizar un producto existente
    @PutMapping("/{id}")
    public Mono<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        return productoService.actualizarProducto(id, productoActualizado);
    }

    // Eliminar un producto por su ID
    @DeleteMapping("/{id}")
    public Mono<Void> eliminarProducto(@PathVariable Long id) {
        return productoService.eliminarProducto(id);
    }

    // Reducir el stock de un producto
    @PostMapping("/{id}/reducir-stock")
    public Mono<Void> reducirStock(@PathVariable Long id, @RequestParam int cantidad) {
        return productoService.reducirStock(id, cantidad);
    }
}
