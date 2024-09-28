package com.store.shopping.drivenAdapters.clients;

import com.store.shopping.drivenAdapters.clients.data.ClientDataMapper;
import com.store.shopping.entities.Client;
import com.store.shopping.entities.IClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ClientsRepoImpl implements IClientRepository {
    @Autowired
    private final ClientsRepository clientsRepository;
    @Autowired
    private final ClientDataMapper clientDataMapper;

    @Override
    public Mono<Client> findClientById(String clientId) {
        return clientsRepository.findByClientId(clientId)
                .map(clientDataMapper::toModel);
    }

    @Override
    public Flux<Client> findAll() {
        return  clientsRepository.findAll()
                .map(clientDataMapper::toModel);
    }

    @Override
    public Mono<Client> createClient(Client client) {
        return clientsRepository.save(clientDataMapper.toRepository(client))
                .map(clientDataMapper::toModel);
    }

    @Override
    public Mono<String> updateClient(String id,Client client) {
        return clientsRepository.findByClientId(id)
                .switchIfEmpty(Mono.error(new ClientNotFoundException("Persona no encontrada con id:" + id)))
                .flatMap(clientData -> clientsRepository.updateClient(client.getName(),
                                client.getLastname(),
                                client.getAddress(),
                                client.getTelephone(),
                                client.getDateOfBirth(),
                                clientData.getId())
                        .then(Mono.just("Usuario actualizado con exito")))
                .onErrorResume(e -> Mono.error(new CustomServiceException("Error actualizando persona "+e)));
    }
    public static class ClientNotFoundException extends RuntimeException {
        public ClientNotFoundException(String message) {
            super(message);
        }
    }
    public static class CustomServiceException extends RuntimeException {
        public CustomServiceException(String message) {
            super(message);
        }
    }
}
