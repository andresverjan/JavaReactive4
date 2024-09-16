package org.example.service;

import org.example.entities.Persona;
import org.example.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository repository;

    public Mono<Persona> getPersonaById(Long id) {
        return repository.findById(id);
    }

    public Flux<Persona> getAllPersonas() {
        return repository.findAll();
    }

    public Mono<Persona> addPersona(Persona person){
        return repository.save(person);
    }

    public Mono<String> updatePersona(Persona person){
        if(person.getId() != null){
            return repository.save(person)
                    .doOnNext(person1 -> System.out.println("Data updating: "+person1))
                    .then(Mono.just("User Updated"));
        }
        return Mono.just("User is not present");
    }

    public Mono<Void> deletePersonaById(Long id){
        if(id == null){
            return Mono.error(new IllegalArgumentException("ID cannot be null"));
        }
        return repository.deleteById(id)
                .doOnNext(unused -> System.out.println("Data delete "+id));
    }

}