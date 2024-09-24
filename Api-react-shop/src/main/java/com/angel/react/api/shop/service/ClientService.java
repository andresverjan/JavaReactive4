package com.angel.react.api.shop.service;

import com.angel.react.api.shop.model.ClientEntity;
import com.angel.react.api.shop.model.PersonEntity;
import com.angel.react.api.shop.repository.ClientRepository;
import com.angel.react.api.shop.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PersonRepository personRepository;

    public Flux<ClientEntity> getAll(){
        return clientRepository.findAll();
    }

    public Mono<ClientEntity> getById(Long id){
        if(id == null){
            return Mono.empty();
        }
        return clientRepository.findById(id)
                .doOnNext(p -> System.out.println("Persona encontrada, id: " + id));
    }

    public Mono<ClientEntity> create(ClientEntity client){
        return clientRepository.save(client)
                .doOnNext(p -> System.out.println("Persona creada, id: " + client.getId()));
    }

    public Mono<ClientEntity> update(ClientEntity client){
        return clientRepository.save(client)
                .doOnNext(p -> System.out.println("Persona actualizada, id: " + client.getId()));
    }

    public Mono<Void> deleteById(Long id) {
        if(id == null){
            return Mono.empty();
        }

        return clientRepository.deleteById(id)
                .doOnNext(p -> System.out.println("Persona eliminada, id: " + id));
    }
}
