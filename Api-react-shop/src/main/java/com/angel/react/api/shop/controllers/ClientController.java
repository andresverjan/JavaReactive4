package com.angel.react.api.shop.controllers;

import com.angel.react.api.shop.model.ClientEntity;
import com.angel.react.api.shop.model.PersonEntity;
import com.angel.react.api.shop.service.ClientService;
import com.angel.react.api.shop.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/client")
@Validated
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/retrieve")
    public Flux<ClientEntity> finAll(){
        return this.clientService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<ClientEntity> finById(@PathVariable Long id){
        return this.clientService.getById(id);
    }

    @PostMapping()
    public Mono<ClientEntity> create(@Valid @RequestBody ClientEntity client){
        return this.clientService.create(client);
    }

    @PutMapping()
    public Mono<ClientEntity> update(@RequestBody ClientEntity client){
        return this.clientService.update(client);
    }

    @DeleteMapping("/{id}")
    public Mono<ClientEntity> deleteById(@PathVariable Long id){
        return this.clientService.deleteById(id)
                .doOnSubscribe(s -> System.out.println(" id eliminada"))
                .doOnError(e -> System.out.println("error"))
                .then(Mono.empty());
    }
}
