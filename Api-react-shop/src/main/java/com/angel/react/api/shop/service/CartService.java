package com.angel.react.api.shop.service;

import com.angel.react.api.shop.model.CartEntity;
import com.angel.react.api.shop.model.CartSummaryEntity;
import com.angel.react.api.shop.repository.CartRepository;
import com.angel.react.api.shop.repository.ClientRepository;
import com.angel.react.api.shop.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ClientRepository clientRepository;
    private final PersonRepository personRepository;

    public Flux<CartEntity> getAll() {
        return cartRepository.findAll()
                .switchIfEmpty(Mono.fromRunnable(() -> log.info("No carts found")))
                .doOnComplete(() -> log.info("Find all carts success"))
                .doOnError(error -> log.error("Error finding all carts: {}", error.getMessage()));
    }

    public Flux<CartEntity> getByClient(Long idCliente) {
        return cartRepository.findByIdClient(idCliente)
                .switchIfEmpty(Mono.fromRunnable(() -> log.info("Cart not found, for client ID: {}", idCliente)))
                .doOnNext(p -> log.info("Cart found, client ID: {}", idCliente))
                .doOnError(error -> log.error("Error finding cart fort client ID {}: {}", idCliente, error.getMessage()));
    }

    public Mono<CartSummaryEntity> getSummaryByClient(Long idCliente) {
        return clientRepository.findById(idCliente)
                .switchIfEmpty(Mono.fromRunnable(() -> log.info("Client not found, ID: {}", idCliente)))
                .flatMap(client -> {
                    return personRepository.findById(client.getIdperson())
                            .flatMap(person -> {
                                return cartRepository.findSummaryByClient(idCliente)
                                        .switchIfEmpty(Mono.fromRunnable(() -> log.info("Cart not found, for client ID: {}", idCliente)))
                                        .map(cartSummary -> {
                                            cartSummary.setAddress(person.getAddress());
                                            log.info("Cart summary found, client: {}", idCliente);
                                            return cartSummary;
                                        });
                            });
                })
                .onErrorResume(error -> {
                    log.error("Error finding summary cart fort client ID {}: {}", idCliente, error.getMessage());
                    return Mono.empty();
                });
    }


    public Flux<CartEntity> create(List<CartEntity> cart) {
        return cartRepository.saveAll(cart)
                .doOnNext(p ->
                        log.info("Product NAME {} added to cart for client NAME {}", p.getNameProduct(), p.getNameClient())
                )
                .onErrorResume(error -> {
                            log.error("Error adding product to cart client NAME {}: {}", cart.get(0).getNameClient(), error.getMessage());
                            return Mono.empty();
                        }
                );
    }

    public Flux<CartEntity> update(List<CartEntity> cart) {
        return cartRepository.saveAll(cart)
                .doOnNext(p ->
                        log.info("Product NAME {} updating to cart for client NAME {}", p.getNameProduct(), p.getNameClient())
                )
                .onErrorResume(error -> {
                            log.error("Error updating product to cart client NAME {}: {}", cart.get(0).getNameClient(), error.getMessage());
                            return Mono.empty();
                        }
                );
    }

    public Mono<ResponseEntity<String>> deleteById(Long id) {
        return cartRepository.findById(id)
                .flatMap(item -> cartRepository.deleteById(id)
                        .then(Mono.fromCallable(() -> {
                            log.info("Product of the cart ID {}, NAME {} deleted", item.getId(), item.getNameProduct());
                            return ResponseEntity.ok("deleted ok");
                        }))
                )
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("Product of the cart ID {} not found, error deleting", id);
                    return Mono.just(ResponseEntity.notFound().build());
                }))
                .doOnError(error -> log.error("Error deleting product ID {} of the cart: {}", id, error.getMessage()));
    }

    public Mono<ResponseEntity<String>> emptyByClient(Long id) {
        return cartRepository.findByIdClient(id)
                .collectList()
                .flatMap(items -> {
                    if (items.isEmpty()) {
                        log.error("Cart not found for client id {} ", id);
                        return Mono.just(ResponseEntity.status(404).body("Cart not found"));
                    }

                    var item = items.get(0);
                    return cartRepository.deleteByIdClient(id)
                            .then(Mono.fromCallable(() -> {
                                log.info("Cart of the client ID {}, NAME {} deleted", item.getIdClient(), item.getNameClient());
                                return ResponseEntity.ok("deleted ok");
                            }));
                })
                .doOnError(error -> log.error("Error deleting cart CLIENT ID {}: {}", id, error.getMessage()));
    }
}
