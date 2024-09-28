package com.programacion.reactiva.trabajo_final.controllers;

import com.programacion.reactiva.trabajo_final.model.Producto;
import com.programacion.reactiva.trabajo_final.service.ProductoService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/producto")
public class ProductoController {


    private ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public Flux<Producto> listarProductos(){
        return productoService.listarProductos();
    }

    @GetMapping("/{id}")
    public Mono<Producto> buscarProducto(@PathVariable Long id){
        return productoService.buscarProducto(id);
    }

    @GetMapping("/nombre/{nombre}")
    public Flux<Producto> buscarProductoPorNombre(@PathVariable String nombre){
        return productoService.buscarProductoPorNombre(nombre);
    }

    @PostMapping
    public Mono<Producto> crearProducto(@RequestBody Producto Producto){
        System.out.println("Creando producto");
        return productoService.crearProducto(Producto);
    }

    @PutMapping("/{id}")
    public Mono<Producto> editarProducto(@PathVariable Long id, @RequestBody Producto Producto){
        return productoService.editarProducto(id, Producto);
    }

    @PutMapping("/stock/{id}")
    public Mono<Producto> actualizarStock(@PathVariable Long id, @RequestParam int cantidad){
        return productoService.actualizarStock(id, cantidad);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> eliminarProducto(@PathVariable Long id){
        return productoService.eliminarProducto(id);
    }

}

