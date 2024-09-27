package com.curso_java.tienda.services;

import com.curso_java.tienda.entities.CarritoCompra;
import com.curso_java.tienda.repositories.CarritoComprasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CarritoComprasService {

    @Autowired
    private CarritoComprasRepository carritoComprasRepository;

    // Crear o actualizar un carrito
    public Mono<CarritoCompra> saveCarrito(CarritoCompra carrito) {
        return carritoComprasRepository.save(carrito);
    }

    // Obtener todos los carritos
    public Flux<CarritoCompra> getAllCarritos() {
        return carritoComprasRepository.findAll();
    }

    // Obtener un carrito por ID
    public Mono<CarritoCompra> getCarritoById(String id) {
        return carritoComprasRepository.findById(id);
    }

    // Eliminar un carrito
    public Mono<Void> deleteCarrito(String id) {
        return carritoComprasRepository.deleteById(id);
    }
}

