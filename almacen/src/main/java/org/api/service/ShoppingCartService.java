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

    private final ConcurrentHashMap<String, ShoppingCart> carritos = new ConcurrentHashMap<>();
    private Boolean enStock = false;
    @Autowired
    private ShoppingCartRepository repository;
    @Autowired
    private ProductRepository productRepository;


    public Mono<Map<Long, Integer> > agregarAlCarrito(String userId, Long productoId, Integer cantidad) {

        ShoppingCart carrito = carritos
                .computeIfAbsent(userId, id -> ShoppingCart.builder()
                        .id(userId)
                        .clientId(userId)
                        .items(new HashMap<>()).build());

        System.out.println(carrito);

        productRepository.findById(productoId)
                .switchIfEmpty(Mono.error(new NullPointerException("El producto no existe")))
                .doOnNext(System.out::println)
                .map(p -> p.getStock() > 0);

        //verificarStock(productoId, cantidad);
                /*.map(stockDisponible -> {
                    System.out.println(stockDisponible);
                    if (stockDisponible) {
                        System.out.println("aquiii");
                        carrito.agregarProducto(productoId, cantidad);
                        return Mono.justOrEmpty(carrito.getItems());
                    } else {
                        return Mono.error(new Exception("No hay stock disponible."));
                    }
                });*/
        //carrito.agregarProducto(productoId,cantidad);
        //acumularTotal(productoId,cantidad).subscribe(items -> carrito.setTotal(carrito.getTotal()+items));

        return Mono.justOrEmpty(carrito.getItems());
    }

    private void comprobarStock(Long productoId, Integer cantidad) {
         productRepository.findById(productoId)
                .switchIfEmpty(Mono.error(new NullPointerException("El producto no existe")))
                .map(product -> product.getStock().compareTo(cantidad) > 0 )
                 .subscribe(inStock -> enStock = inStock);

    }

    public Mono<Boolean> verificarStock(Long productoId, Integer cantidad) {
        System.out.println("STOCK!!!");
        return productRepository.findById(productoId)
                .switchIfEmpty(Mono.error(new NullPointerException("El producto no existe")))
                .doOnNext(System.out::println)
                .map(p -> {
                    System.out.println(p);
                    return p.getStock() > 0;
                });
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


/*
    public Mono<Double> devolverTotal(String userId) {
        //return Mono.just(carritos.get(userId).getTotal());
        return
                carritos.get(userId).getItems().entrySet().stream()
                        .mapToDouble(entry -> {
                            obtenerPrecioProducto(entry.getKey())
                                    .map(precio -> precio* entry.getValue());
                                    
                            return Mono.just(2.9);
                        }).sum();

        //return Mono.just(10.0);}

    }*/


    private Mono<Double> obtenerPrecioProducto(Long productoId) {
        return productRepository.findById(productoId).map(Product::getPrice);
    }

    private void actualizarStock(Long productoId, Integer cantidad){

        productRepository.findById(productoId)
                .flatMap(existingProduct -> {
                    existingProduct.setStock(existingProduct.getStock() - cantidad);
                    return productRepository.save(existingProduct);
                });

    }

}
