package org.api.service;

import org.api.model.Product;
import org.api.model.ShoppingCart;
import org.api.repository.SaleProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ShoppingCartService {

    private final ConcurrentHashMap<String, ShoppingCart> carritos = new ConcurrentHashMap<>();

    @Autowired
    private SaleProductRepository saleProductRepository;


    public Mono<Product> getProductById(String userId, Long productoId, Integer cantidad) {

        return saleProductRepository.findById(productoId)
                .flatMap(producto -> {
                    if (producto.getStock() >= cantidad) {
                        agregarAlCarrito(userId,productoId,cantidad);
                        producto.setStock(producto.getStock() - cantidad);
                        return saleProductRepository.save(producto);
                    } else {
                        return Mono.error(new IllegalArgumentException("Stock insuficiente"));
                    }
                });

    }

    public Map<Long, Integer> agregarAlCarrito(String userId, Long productoId, Integer cantidad) {

        ShoppingCart carrito = carritos
                .computeIfAbsent(userId, id -> ShoppingCart.builder()
                        .id(userId)
                        .clientId(userId)
                        .items(new HashMap<>()).build());

        carrito.agregarProducto(productoId,cantidad);
        return carrito.getItems();
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

    public Mono<Product> actualizarCantidad(String userId, Long productoId, int cantidad) {
        ShoppingCart carrito = carritos.get(userId);

        if (carrito != null) {
            return saleProductRepository.findById(productoId)
                    .flatMap(producto -> {
                        if (producto.getStock() >= cantidad) {
                            carrito.actualizarCantidad(productoId,cantidad);
                            producto.setStock(producto.getStock() - cantidad);
                            return saleProductRepository.save(producto);
                        } else {
                            return Mono.error(new IllegalArgumentException("Stock insuficiente"));
                        }
                    });
        } return null;
    }

    public Mono<ShoppingCart> vaciarCarrito(String userId) {
        ShoppingCart carrito = carritos.get(userId);
        if (carrito != null) {
            carrito.vaciar();
        }
        return Mono.just(carrito);
    }


    private Mono<Double> obtenerPrecioProducto(Long productoId) {
        return saleProductRepository.findById(productoId).map(Product::getPrice);
    }

}
