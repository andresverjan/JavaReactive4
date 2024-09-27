package com.curso_java.tienda.controllers;

import com.curso_java.tienda.dtos.ProductoDTO;
import com.curso_java.tienda.dtos.ResponseData;
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
    public Mono<ResponseData<ProductoDTO>> createProducto(@RequestBody Producto producto) {
        return productoService.saveProducto(producto)
                .map(productoGuardado -> new ResponseData<>("Producto creado exitosamente", productoGuardado));
    }

    @GetMapping
    public Flux<ResponseData<ProductoDTO>> getAllProductos() {
        return productoService.getAllProductos()
                .map(producto -> new ResponseData<>("Producto encontrado", producto));
    }

    @GetMapping("/{id}")
    public Mono<ResponseData<ProductoDTO>> getProductoById(@PathVariable String id) {
        return productoService.getProductoById(id)
                .map(producto -> new ResponseData<>("Producto encontrado", producto))
                .defaultIfEmpty(new ResponseData<>("Producto no encontrado", null));
    }

    @PutMapping("/{id}")
    public Mono<ResponseData<ProductoDTO>> updateProducto(@PathVariable String id, @RequestBody Producto producto) {
        return productoService.updateProducto(id, producto)
                .map(productoActualizado -> new ResponseData<>("Producto actualizado exitosamente", productoActualizado))
                .defaultIfEmpty(new ResponseData<>("Producto no encontrado", null));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseData<ProductoDTO>> deleteProducto(@PathVariable String id) {
        return productoService.deleteProducto(id)
                .map(producto -> new ResponseData<>("Producto eliminado exitosamente", producto))
                .defaultIfEmpty(new ResponseData<>("Producto no encontrado", null));
    }
}