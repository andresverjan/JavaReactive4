package com.angel.react.api.shop.service;

import com.angel.react.api.shop.model.PersonEntity;
import com.angel.react.api.shop.model.SupplierEntity;
import com.angel.react.api.shop.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public Flux<SupplierEntity> getAll(){
        return supplierRepository.findAll();
    }

    public Mono<SupplierEntity> getById(Long id){
        if(id == null){
            return Mono.empty();
        }

        return supplierRepository.findById(id)
                .doOnNext(p -> System.out.println("Persona encontrada, id: " + id));
    }

    public Mono<SupplierEntity> create(SupplierEntity person){
        return supplierRepository.save(person)
                .doOnNext(p -> System.out.println("Persona creada, id: " + person.getId()));
    }

    public Mono<SupplierEntity> update(SupplierEntity person){
        return supplierRepository.save(person)
                .doOnNext(p -> System.out.println("Persona actualizada, id: " + person.getId()));
    }

    public Mono<Void> deleteById(Long id) {
        if(id == null){
            return Mono.empty();
        }

        return supplierRepository.deleteById(id)
                .doOnNext(p -> System.out.println("Persona eliminada, id: " + id));
    }
}
