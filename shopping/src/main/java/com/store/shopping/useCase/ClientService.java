package com.store.shopping.useCase;

import com.store.shopping.drivenAdapters.clients.ClientsRepoImpl;
import com.store.shopping.entities.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClientService {
    @Autowired
    private final ClientsRepoImpl clientsRepoImpl;

    public Mono<Client> getClient(String id){
        return clientsRepoImpl.findClientById(id);
    }
    public Mono<Client> createClient(Client client){
        return clientsRepoImpl.createClient(client);
    }
    public Flux<Client> getClients(){
        return clientsRepoImpl.findAll();
    }
    public Mono<String> updateClient(String id,Client client){
        return clientsRepoImpl.updateClient(id,client);
    }

}
