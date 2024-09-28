package com.store.shopping.entities;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClientRepository {
    Mono<Client> findClientById(String clientId);
    Flux<Client> findAll();
    Mono<Client> createClient(Client client);
    Mono<String> updateClient(String id,Client client);
}
