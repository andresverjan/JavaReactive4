package org.api.service;

import org.api.model.Product;
import org.api.model.ShoppingCart;
import org.api.repository.ProductRepository;
import org.api.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository repository;

    @Autowired
    private ProductRepository productRepository;

    private final ConcurrentHashMap<String, ShoppingCart> carritos = new ConcurrentHashMap<>();

    public Mono<ShoppingCart> agregarAlCarrito(String userId, Long productoId, Integer cantidad) {
        ShoppingCart carrito =  carritos
                .computeIfAbsent(userId, id -> ShoppingCart
                        .builder()
                        .clientId(userId)
                        .total(1245.98)
                        .items(new HashMap<>()).build());
        carrito.agregarProducto(productoId, cantidad);
        return Mono.justOrEmpty(carrito);
    }


    public Mono<ShoppingCart> obtenerContenidoCarrito(String userId) {
        ShoppingCart carrito = carritos.get(userId);
        return Mono.justOrEmpty(carrito);
    }

    public Mono<ShoppingCart> eliminarDelCarrito(String userId, Long productoId) {
        ShoppingCart carrito = carritos.get(userId);
        if (carrito != null) {
            carrito.eliminarProducto(productoId);
        }
        return Mono.just(carrito);
    }

    public Mono<ShoppingCart> actualizarCantidad(String userId, Long productoId, int cantidad) {
        ShoppingCart carrito = carritos.get(userId);
        if (carrito != null) {
            carrito.actualizarCantidad(productoId, cantidad);
        }
        return Mono.just(carrito);
    }

    public Mono<ShoppingCart> vaciarCarrito(String userId) {
        ShoppingCart carrito = carritos.get(userId);
        if (carrito != null) {
            carrito.vaciar();
        }
        return Mono.just(carrito);
    }

    public Mono<Double> calcularTotal(String userId) {
        ShoppingCart carrito = carritos.get(userId);
        double total = 0.0;

        if (carrito != null) {
            for (Map.Entry<Long, Integer> entry : carrito.getItems().entrySet()) {
                total = 0;
                Mono<Double> precio = obtenerPrecioProducto(entry.getKey())
                        .map(pr -> pr * entry.getValue());

            }
        }

        return Mono.just(total);
    }

    private Mono<Double> obtenerPrecioProducto(Long productoId) {

        return productRepository.findById(productoId).map(Product::getPrice);

    }

}
