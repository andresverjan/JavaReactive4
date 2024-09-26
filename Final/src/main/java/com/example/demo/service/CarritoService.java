package com.example.demo.service;

import com.example.demo.model.Carrito;
import com.example.demo.model.Producto;
import com.example.demo.repository.CarritoRepository;
import com.example.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // Método para obtener todos los productos en el carrito de un usuario
    public Flux<Carrito> getCarritoContents(Long userId) {
        return carritoRepository.findByUserId(userId);
    }

    public Mono<String> addItemToCarrito(Long userId, Carrito item) {
        return productoRepository.findById(item.getProductoId())
                .flatMap(producto -> carritoRepository.findByUserId(userId)
                        .collectList()
                        .flatMap(carritoItems -> {
                            Optional<Carrito> existingProduct = carritoItems.stream()
                                    .filter(c -> c.getProductoId().equals(producto.getId()))
                                    .findFirst();

                            if (existingProduct.isPresent()) {
                                // Si el producto ya está en el carrito, actualizar la cantidad
                                existingProduct.get().setQuantity(existingProduct.get().getQuantity() + item.getQuantity());
                                return carritoRepository.save(existingProduct.get())
                                        .then(Mono.just("Producto actualizado en el carrito con éxito!"));
                            } else {
                                // Si es un producto nuevo en el carrito, agregarlo
                                Carrito nuevoCarritoItem = new Carrito();
                                nuevoCarritoItem.setProductoId(producto.getId());
                                nuevoCarritoItem.setUserId(userId);  // Asociar el producto al carrito del usuario
                                nuevoCarritoItem.setName(producto.getName());
                                nuevoCarritoItem.setPrice(producto.getPrice());
                                nuevoCarritoItem.setQuantity(item.getQuantity());

                                // El ID no se asigna manualmente, ya que será generado por la base de datos
                                return carritoRepository.save(nuevoCarritoItem)
                                        .then(Mono.just("Producto agregado al carrito con éxito!"));
                            }
                        })
                )
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El producto no existe")));
    }

    // Método para eliminar un producto del carrito
    public Mono<Void> removeItemFromCarrito(Long id) {
        return carritoRepository.deleteById(id);
    }

    // Método para actualizar la cantidad de un producto en el carrito
    public Mono<Carrito> updateItemQuantity(Long id, int quantity) {
        return carritoRepository.findById(id)
                .flatMap(item -> {
                    item.setQuantity(quantity);
                    return carritoRepository.save(item);
                });
    }

    // Método para vaciar el carrito de un usuario
    public Mono<Void> clearCarrito(Long userId) {
        return carritoRepository.findByUserId(userId)
                .flatMap(carritoRepository::delete)
                .then();
    }

    // Método para calcular el total del carrito de un usuario
    public Mono<Double> calculateTotal(Long userId) {
        return carritoRepository.findByUserId(userId)
                .map(item -> item.getPrice() * item.getQuantity())
                .reduce(Double::sum);
    }
}