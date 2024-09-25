package com.example.shopping_cart.service;

import com.example.shopping_cart.model.Client;
import com.example.shopping_cart.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public Mono<Client> createClient(Client client){
     //validacion de dats errores

     return clientRepository.save(client);
    }

    public  Mono<String> updateClient(Client client){
        if (client.getId() == null){
            return Mono.just("Id client invalid");
        }
        return clientRepository.findByEmail(client.getEmail())
                .flatMap(existClient->{
                    return clientRepository.save(client)
                            .doOnNext(clientUpdate->{
                                System.out.println("Data Client update: "+clientUpdate);
                            })
                            .then(Mono.just("client update "+client.toString()));
                })
                .switchIfEmpty(Mono.just("client not found"));
    }

    public Mono<Client> clientByEmail(String email){
        if (email==null){
            return Mono.empty();
        }
        return clientRepository.findByEmail(email)
                .doOnNext(client-> System.out.println("Data Client: "+client))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Client not found by email: "+email)))
                .onErrorResume(e -> {
                    System.out.println("Error during search: " + e.getMessage());
                    return Mono.empty();
                });
    }

    public Mono<String> deleteClient(String email){
        if (email==null){
            return Mono.just("email no valido");
        }
        return clientRepository.findByEmail(email)
                .flatMap(existClient-> clientRepository.deleteById(existClient.getId())
                        .doOnSuccess(clientDelete->{
                            System.out.println("client delete: "+existClient.toString());
                        })
                        .then(Mono.just("client delete "+existClient.toString())))
                .switchIfEmpty(Mono.just("Client not foud by email: "+email));
    }
}
