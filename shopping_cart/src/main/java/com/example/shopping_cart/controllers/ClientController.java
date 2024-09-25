package com.example.shopping_cart.controllers;

import com.example.shopping_cart.model.Client;
import com.example.shopping_cart.model.Product;
import com.example.shopping_cart.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;
    @PostMapping
    public Mono<Client> create(@RequestBody Client c){return clientService.createClient(c);}

    @GetMapping("/{email}")
    public Mono<Client> getClientByEmail(@PathVariable String email){return  clientService.clientByEmail(email);}

    @PutMapping
    public Mono<String> updateClient(@RequestBody Client client){return clientService.updateClient(client);}

    @DeleteMapping("/delete/{email}")
    public Mono<String> deleteClient(@PathVariable String email){return clientService.deleteClient(email);}
}
