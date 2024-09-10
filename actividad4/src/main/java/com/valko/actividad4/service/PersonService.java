package com.valko.actividad4.service;

import com.valko.actividad4.models.Person;
import com.valko.actividad4.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    public Mono<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    /*public Mono<Person> createPerson(Person person) {
        return Mono.just(new Person(2L, "Sebastian", 30));
    }

    public Mono<Person> updatePerson(Person person) {
        return Mono.just(new Person(3L, "Juan", 30));
    }

    public Mono<Object> deletePersonById(Long id) {
        return Mono.empty();
    }*/
}
