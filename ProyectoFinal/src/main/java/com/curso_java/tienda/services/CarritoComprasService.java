package com.curso_java.tienda.services;

import com.curso_java.tienda.entities.CarritoCompra;
import com.curso_java.tienda.repositories.CarritoComprasRepository;
import com.curso_java.tienda.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Servicio para gestionar carritos de compra.
 */
@Service
public class CarritoComprasService {

    @Autowired
    private CarritoComprasRepository carritoComprasRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Crea un nuevo carrito de compra para un cliente específico.
     * No crea un nuevo carrito si el cliente ya tiene uno.
     *
     * @param clienteId El ID del cliente.
     * @return Un Mono que emite el carrito de compra creado o un Mono vacío si el cliente no existe o ya tiene un carrito.
     */
    public Mono<CarritoCompra> createCarrito(String clienteId) {
        // Buscar el usuario por ID
        return usuarioRepository.findById(clienteId)
                .flatMap(usuario ->
                        // Verificar si el usuario ya tiene un carrito
                        carritoComprasRepository.findByUsuarioId(clienteId)
                                .hasElements()
                                .flatMap(exists -> {
                                    if (exists) {
                                        // Si el usuario ya tiene un carrito, retornar un Mono vacío
                                        return Mono.empty();
                                    } else {
                                        // Si el usuario no tiene un carrito, crear uno nuevo
                                        CarritoCompra carritoCompra = new CarritoCompra(clienteId, LocalDateTime.now(), LocalDateTime.now());
                                        return carritoComprasRepository.save(carritoCompra);
                                    }
                                })
                )
                // Si el usuario no existe, retornar un Mono vacío
                .switchIfEmpty(Mono.empty());
    }

    /**
     * Obtiene todos los carritos de compra.
     *
     * @return Un Flux que emite la lista de todos los carritos de compra.
     */
    public Flux<CarritoCompra> getAllCarritos() {
        return carritoComprasRepository.findAll()
                .switchIfEmpty(Flux.empty());
    }

    /**
     * Obtiene un carrito de compra por su ID.
     *
     * @param id El ID del carrito de compra.
     * @return Un Mono que emite el carrito de compra correspondiente al ID proporcionado o un Mono vacío si no se encuentra.
     */
    public Mono<CarritoCompra> getCarritoById(String id) {
        return carritoComprasRepository.findById(id)
                .switchIfEmpty(Mono.empty());
    }

    /**
     * Elimina un carrito de compra por su ID.
     *
     * @param id El ID del carrito de compra a eliminar.
     * @return Un Mono que emite el carrito de compra eliminado o un Mono vacío si no se encuentra el carrito.
     */
    public Mono<CarritoCompra> deleteCarrito(String id) {
        return carritoComprasRepository.findById(id)
                .flatMap(
                        carritoCompra -> carritoComprasRepository.delete(carritoCompra)
                                .then(Mono.just(carritoCompra))
                )
                .switchIfEmpty(Mono.empty());
    }
}