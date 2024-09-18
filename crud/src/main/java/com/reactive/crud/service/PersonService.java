package com.reactive.crud.service;

import com.reactive.crud.models.Person;
import com.reactive.crud.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public Flux<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Mono<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public Mono<Person> createPerson(Person person) {
        return personRepository.save(person);
    }
    public Mono<Person> updatePerson(Long id, Person personUpdate) {
        return personRepository.findById(id)
                .flatMap(personExists -> {
                    if (personUpdate.getAge()!=null){personExists.setAge(personUpdate.getAge());};
                    if (personUpdate.getName()!=null){personExists.setName(personUpdate.getName());};
                    if (personUpdate.getGender()!=null){personExists.setGender(personUpdate.getGender());};
                    if (personUpdate.getDateOfBirth()!=null){personExists.setDateOfBirth(personUpdate.getDateOfBirth());};
                    if (personUpdate.getBloodType()!=null){personExists.setBloodType(personUpdate.getBloodType());};
                    return personRepository.save(personExists);

                }).doOnError(error -> System.out.println("Error actualizando la persona: "+error.getMessage()))
                .doOnSuccess(System.out::println);
    }

    public Mono<Void> deletePersonById(Long id) {
        return personRepository.deleteById(id);
    }
}