package com.example.EntregaFinal.controllers;

import com.example.EntregaFinal.model.Carrito;
import com.example.EntregaFinal.repository.ProductoRepository;
import com.example.EntregaFinal.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    private final CarritoService carritoService;
    private final ProductoRepository productoRepository;


    public CarritoController(CarritoService carritoService, ProductoRepository productoRepository) {
        this.carritoService = carritoService;
        this.productoRepository = productoRepository;
    }

    // Crear un nuevo carrito vacío
    @PostMapping
    public Mono<Carrito> crearCarrito() {
        return carritoService.crearCarrito();
    }

    // Agregar un producto al carrito
    @PostMapping("/{carritoId}/agregar")
    public Mono<Carrito> agregarProductoAlCarrito(@PathVariable Long carritoId, @RequestParam Long productoId, @RequestParam int cantidad) {
        return carritoService.agregarProductoAlCarrito(carritoId, productoId, cantidad);
    }

    // Obtener un carrito por ID
    @GetMapping("/{carritoId}")
    public Mono<Carrito> obtenerCarritoPorId(@PathVariable Long carritoId) {
        return carritoService.obtenerCarritoPorId(carritoId);
    }

    // Eliminar un producto del carrito
    @DeleteMapping("/{carritoId}/eliminar")
    public Mono<Carrito> eliminarProductoDelCarrito(@PathVariable Long carritoId) {
        return carritoService.eliminarProductoDelCarrito(carritoId);
    }

    // Vaciar el carrito
    @DeleteMapping("/{carritoId}/vaciar")
    public Mono<Carrito> vaciarCarrito(@PathVariable Long carritoId) {
        return carritoService.vaciarCarrito(carritoId);
    }

    // Actualizar la cantidad de un producto en el carrito
    @PutMapping("/{carritoId}/actualizar-cantidad")
    public Mono<Carrito> actualizarCantidadProducto(
            @PathVariable Long carritoId,
            @RequestParam int nuevaCantidad) {
        return carritoService.actualizarCantidadProducto(carritoId, nuevaCantidad);
    }

    // Calcular el total del carrito
    @GetMapping("/{carritoId}/calcular-total")
    public Mono<Double> calcularTotalCarrito(@PathVariable Long carritoId) {
        return carritoService.calcularTotalCarrito(carritoId, productoRepository);
    }
}
