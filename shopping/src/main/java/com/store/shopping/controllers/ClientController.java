package com.store.shopping.controllers;

import com.store.shopping.entities.Client;
import com.store.shopping.useCase.ClientService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/{id}")
    public Mono<Client> getClientById(@PathVariable String id) {
        return clientService.getClient(id);
    }
    @PostMapping
    public Mono<Client> createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }
    @GetMapping
    public Flux<Client> getClients() {
        return clientService.getClients();
    }
    @PutMapping("/{id}")
    public Mono<String> updateClient(@PathVariable String id,@RequestBody Client client) {
        return clientService.updateClient(id,client);
    }
}
