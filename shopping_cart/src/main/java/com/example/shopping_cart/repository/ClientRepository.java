package com.example.shopping_cart.repository;


import com.example.shopping_cart.model.Client;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ClientRepository extends ReactiveCrudRepository<Client, Integer> {
    Mono<Client> findByEmail(String email);

}
