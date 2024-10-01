package com.example.EntregaFinal.service;

import com.example.EntregaFinal.model.Carrito;
import com.example.EntregaFinal.model.ProductoCarrito;
import com.example.EntregaFinal.repository.CarritoRepository;
import com.example.EntregaFinal.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;

    public CarritoService(CarritoRepository carritoRepository) {
        this.carritoRepository = carritoRepository;
    }

    // Crear un carrito vacío
    public Mono<Carrito> crearCarrito() {
        Carrito carrito = new Carrito();
        carrito.setProductoId(null);
        carrito.setCantidad(0);
        return carritoRepository.save(carrito);
    }

    // Agregar un producto al carrito
    public Mono<Carrito> agregarProductoAlCarrito(Long carritoId, Long productoId, int cantidad) {
        return carritoRepository.findById(carritoId)
                .flatMap(carrito -> {
                    carrito.setProductoId(productoId);
                    carrito.setCantidad(cantidad);
                    return carritoRepository.save(carrito);
                });
    }

    // Obtener el carrito por su ID
    public Mono<Carrito> obtenerCarritoPorId(Long carritoId) {
        return carritoRepository.findById(carritoId);
    }

    // Eliminar un producto del carrito
    public Mono<Carrito> eliminarProductoDelCarrito(Long carritoId) {
        return carritoRepository.findById(carritoId)
                .flatMap(carrito -> {
                    carrito.setProductoId(null);
                    carrito.setCantidad(0);
                    return carritoRepository.save(carrito);
                });
    }

    // Vaciar el carrito
    public Mono<Carrito> vaciarCarrito(Long carritoId) {
        return eliminarProductoDelCarrito(carritoId);
    }

    // Actualizar la cantidad de un producto en el carrito
    public Mono<Carrito> actualizarCantidadProducto(Long carritoId, int nuevaCantidad) {
        return carritoRepository.findById(carritoId)
                .flatMap(carrito -> {
                    carrito.setCantidad(nuevaCantidad);
                    return carritoRepository.save(carrito);
                });
    }


    // Calcular el total del carrito
    public Mono<Double> calcularTotalCarrito(Long carritoId, ProductoRepository productoRepository) {
        return carritoRepository.findById(carritoId)
                .flatMap(carrito -> {
                    Long productoId = carrito.getProductoId();
                    if (productoId != null) {
                        return productoRepository.findById(productoId)
                                .map(producto -> producto.getPrice() * carrito.getCantidad());
                    } else {
                        return Mono.just(0.0);
                    }
                });
    }



}
