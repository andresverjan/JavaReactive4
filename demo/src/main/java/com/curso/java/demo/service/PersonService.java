package com.curso.java.demo.service;

import com.curso.java.demo.model.Person;
import com.curso.java.demo.repository.PersonRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Flux<Person> getAllPeople(){
        return personRepository.findAll()
                .doOnNext(person -> System.out.println("Consultando personas " + person));
    }

    public Mono<Person> getPersonById(Long id){

        return personRepository.findById(id)
                .doOnNext(person -> System.out.println("Consultando persona por id " + person));
    }

    public Mono<Person> createPerson(Person person){
        return personRepository.save(person)
                .doOnNext(persona -> System.out.println("Creando persona " + persona));
    }

    public Mono<Person> updatePerson(Long id, Person person){
            personRepository.save(person);
            return Mono.just(person);

    }

    public Mono<String> deletePerson(Long id){
        return Mono.just("Persona con ID " + id + "ha sido eliminada con exito.");
    }
}
