package com.angel.react.api.shop.service;

import com.angel.react.api.shop.model.CartEntity;
import com.angel.react.api.shop.model.ClientEntity;
import com.angel.react.api.shop.repository.CartRepository;
import com.angel.react.api.shop.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.lang.System.*;

@Slf4j
@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Flux<CartEntity> getAll(){
        return cartRepository.findAll();
    }

    public Flux<CartEntity> getByClient(Long idCliente){
        if(idCliente == null){
            return Flux.empty();
        }

        return cartRepository.findByIdClient(idCliente)
                .doOnNext(p -> log.info("Cart getted, client: {}", idCliente));
    }


    public Flux<CartEntity> create(List<CartEntity> cart){
        return cartRepository.saveAll(cart)
                .doOnNext(p -> log.info("Producto agregado al carrito, id: {}", p.getId()));
    }

    public Flux<CartEntity> update(List<CartEntity> cart){
        return cartRepository.saveAll(cart)
                .doOnNext(p -> log.info("Producto modificado al carrito, id: {}", p.getId()));
    }

    public Mono<ResponseEntity<String>> deleteById(Long id) {
        if(id == null){
            return Mono.empty();
        }
        return cartRepository.findById(id)
                .flatMap(item -> cartRepository.deleteById(id)
                        .then(Mono.fromCallable(() -> {
                            log.info("item of the cart ID {}, NAME {} deleted", item.getId(), item.getNameProduct());
                            return ResponseEntity.ok("deleted ok");
                        }))
                )
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("item of the cart ID {} not found, error deleting", id);
                    return Mono.just(ResponseEntity.notFound().build());
                }))
                .doOnError(error -> log.error("Error deleting product ID {} of the cart: {}", id, error.getMessage()));
    }

    public Mono<Void> emptyByClient(Long id) {
        if(id == null){
            return Mono.empty();
        }
        return cartRepository.deleteByIdClient(id);
    }
}
