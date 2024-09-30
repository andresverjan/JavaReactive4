package com.angel.react.api.shop.service;

import com.angel.react.api.shop.model.ClientEntity;
import com.angel.react.api.shop.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Flux<ClientEntity> getAll(){
        return clientRepository.findAll()
                .switchIfEmpty(Mono.fromRunnable(() -> log.info("No clients found")))
                .doOnComplete(() -> log.info("Find all clients success"))
                .doOnError(error -> log.error("Error finding all clients: {}", error.getMessage()));
    }

    public Mono<ClientEntity> getById(Long id){
        return clientRepository.findById(id)
                .switchIfEmpty(Mono.fromRunnable(() -> log.info("Client not found, id: {}", id)))
                .doOnNext(p -> log.info("Client found, id: {}", id))
                .doOnError(error -> log.error("Error finding the client ID {}: {}", id, error.getMessage()));
    }

    public Mono<ClientEntity> create(ClientEntity client){
        return clientRepository.save(client)
                .doOnNext(p -> log.info("Client created, id: {}", client.getId()))
                .onErrorResume(error -> {
                            log.error("Error creating the client ID_PERSON {}: {}", client.getIdperson(), error.getMessage());
                            return Mono.empty();
                        }
                );
    }

    public Mono<ClientEntity> update(ClientEntity client){
        return clientRepository.save(client)
                .doOnNext(p -> log.info("Client update, id: {}", client.getId()))
                .onErrorResume(error -> {
                    log.error("Error updating the client ID {}: {}", client.getId(), error.getMessage());
                    return Mono.empty();
                });
    }

    public Mono<ResponseEntity<String>> deleteById(Long id) {
        return clientRepository.findById(id)
                .flatMap(item -> clientRepository.deleteById(id)
                        .then(Mono.fromCallable(() -> {
                            log.info("Client [{}] deleted", item.getId());
                            return ResponseEntity.ok("deleted ok");
                        }))
                )
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("Client ID {} not found, error deleting", id);
                    return Mono.just(ResponseEntity.notFound().build());
                }))
                .doOnError(error -> log.error("Error deleting client ID {}: {}", id, error.getMessage()));
    }
}
