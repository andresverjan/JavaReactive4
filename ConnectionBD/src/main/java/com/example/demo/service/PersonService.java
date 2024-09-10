package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Flux<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Mono<Person> getPersonById(Long id) {
        return personRepository.findById(id)
                .switchIfEmpty(Mono.error(new PersonNotFoundException("Persona no encontrada con id:" + id)))
                .onErrorResume(e -> Mono.error(new CustomServiceException("Service error")));
    }

    public Mono<Person> createPerson(Person person) {
        return personRepository.save(person);
    }

    public Mono<Person> updatePerson(Long id, Person updatedPerson) {
        return personRepository.findById(id)
                .switchIfEmpty(Mono.error(new PersonNotFoundException("Persona no encontrada con id: " + id)))
                .flatMap(existingPerson -> {
                    existingPerson.setName(updatedPerson.getName());
                    existingPerson.setAge(updatedPerson.getAge());
                    existingPerson.setGender(updatedPerson.getGender());
                    existingPerson.setDateOfBirth(updatedPerson.getDateOfBirth());
                    existingPerson.setBloodType(updatedPerson.getBloodType());
                    return personRepository.save(existingPerson);
                })
                .onErrorResume(e -> Mono.error(new CustomServiceException("Error actualizando persona")));
    }

    public Mono<Void> deletePerson(Long id) {
        return personRepository.deleteById(id);
    }

    public static class PersonNotFoundException extends RuntimeException {
        public PersonNotFoundException(String message) {
            super(message);
        }
    }

    public static class CustomServiceException extends RuntimeException {
        public CustomServiceException(String message) {
            super(message);
        }
    }


}