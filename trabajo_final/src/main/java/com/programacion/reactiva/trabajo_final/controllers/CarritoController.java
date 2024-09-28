package com.programacion.reactiva.trabajo_final.controllers;

import com.programacion.reactiva.trabajo_final.model.CarritoProducto;
import com.programacion.reactiva.trabajo_final.model.dto.CarritoComprasDTO;
import com.programacion.reactiva.trabajo_final.model.dto.CarritoProductoDTO;
import com.programacion.reactiva.trabajo_final.service.CarritoService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/carrito")
public class CarritoController {
        private CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @PostMapping
    public Mono<CarritoProductoDTO> agregarProductoAlCarrito(@RequestBody CarritoProducto carritoProducto){
        return carritoService.agregarProductoAlCarrito(carritoProducto);
    }

    @PutMapping("/actualizar/product")
    public Mono<CarritoProductoDTO> actualizarCantidadProducto(@RequestParam Long carritoId, @RequestParam Long productoId, @RequestParam int cantidad){
        return carritoService.actualizarCantidadProducto(carritoId, productoId, cantidad);
    }

    @GetMapping("/{id}")
    public Flux<CarritoComprasDTO> listarProductosPorCarrito(@PathVariable Long id){
        return carritoService.obtenerCarrito(id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> eliminarCarrito(@PathVariable Long id){
        return carritoService.eliminarCarrito(id);
    }

    @DeleteMapping("/producto")
    public Mono<Void> eliminarProductoCarrito(@RequestParam Long carritoId, @RequestParam Long productoId){
        return carritoService.eliminarProductoCarrito(carritoId, productoId);
    }
}
