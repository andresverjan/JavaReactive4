package com.curso_java.tienda.controllers;

import com.curso_java.tienda.dtos.ResponseData;
import com.curso_java.tienda.entities.CarritoCompra;
import com.curso_java.tienda.services.CarritoComprasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Controlador REST para gestionar carritos de compra.
 */
@RestController
@RequestMapping("/carritos")
public class CarritoComprasController {

    @Autowired
    private CarritoComprasService carritoComprasService;

    /**
     * Crea un nuevo carrito de compra para un cliente específico.
     *
     * @param clienteId El ID del cliente.
     * @return Un Mono que emite la respuesta con el carrito de compra creado o un mensaje de error si el cliente no se encuentra.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseData<CarritoCompra>> createCarrito(@RequestParam String clienteId) {
        return carritoComprasService.createCarrito(clienteId)
                .map(carritoCompra -> new ResponseData<>("Carrito creado", carritoCompra))
                .defaultIfEmpty(new ResponseData<>("Usuario no encontrado o ya tiene un carrito creado", null));
    }

    /**
     * Obtiene todos los carritos de compra.
     *
     * @return Un Mono que emite la respuesta con la lista de todos los carritos de compra o un mensaje de error si no hay carritos.
     */
    @GetMapping
    public Mono<ResponseData<List<CarritoCompra>>> getAllCarritos() {
        return carritoComprasService.getAllCarritos().collectList()
                .map(carritos -> new ResponseData<>("Lista de carritos", carritos))
                .defaultIfEmpty(new ResponseData<>("No hay carritos", null));
    }

    /**
     * Obtiene un carrito de compra por su ID.
     *
     * @param id El ID del carrito de compra.
     * @return Un Mono que emite la respuesta con el carrito de compra correspondiente al ID proporcionado o un mensaje de error si no se encuentra.
     */
    @GetMapping("/{id}")
    public Mono<ResponseData<CarritoCompra>> getCarritoById(@PathVariable String id) {
        return carritoComprasService.getCarritoById(id)
                .map(carritoCompra -> new ResponseData<>("Carrito encontrado", carritoCompra))
                .defaultIfEmpty(new ResponseData<>("Carrito no encontrado", null));
    }

    /**
     * Elimina un carrito de compra por su ID.
     *
     * @param id El ID del carrito de compra a eliminar.
     * @return Un Mono que emite la respuesta con el carrito de compra eliminado o un mensaje de error si no se encuentra.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseData<CarritoCompra>> deleteCarrito(@PathVariable String id) {
        return carritoComprasService.deleteCarrito(id)
                .map(carritoCompra -> new ResponseData<>("Carrito eliminado", carritoCompra))
                .defaultIfEmpty(new ResponseData<>("Carrito no encontrado", null));
    }
}