package com.example.RestReact.service;

import com.example.RestReact.model.PersonEntity;
import com.example.RestReact.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Flux<PersonEntity> getAll(){
        return personRepository.findAll();
    }

    public Mono<PersonEntity> getById(Long id){
        if(id == null){
            return Mono.empty();
        }

        return personRepository.findById(id)
                .doOnNext(p -> System.out.println("Persona encontrada, id: " + id));
    }

    public Mono<PersonEntity> create(PersonEntity person){
        return personRepository.save(person)
                .doOnNext(p -> System.out.println("Persona creada, id: " + person.getId()));
    }

    public Mono<Void> deleteById(Long id) {
        if(id == null){
            return Mono.empty();
        }

        return personRepository.deleteById(id)
                .doOnNext(p -> System.out.println("Persona eliminada, id: " + id));
    }
}
