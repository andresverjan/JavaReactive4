package com.example.demo.service;

import com.example.demo.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.example.demo.repository.PersonRepository;

@Service
    public class PersonService {

        @Autowired
        private PersonRepository personRepository;

        public Mono<Person> createPerson(Person person) {
            return personRepository.save(person);
        }

        public Flux<Person> getAllPersons() {
            return personRepository.findAll();
        }

        public Mono<Person> getPersonById(Long id) {
            return personRepository.findById(id);
        }

        public Mono<Person> updatePerson(Long id, Person person) {
            return personRepository.findById(id)
                    .flatMap(existingPerson -> {
                        existingPerson.setName(person.getName());
                        existingPerson.setAge(person.getAge());
                        existingPerson.setGender(person.getGender());
                        existingPerson.setDateOfBirth(person.getDateOfBirth());
                        existingPerson.setBloodType(person.getBloodType());
                        return personRepository.save(existingPerson);
                    });
        }

        public Mono<Void> deletePerson(Long id) {
            return personRepository.deleteById(id);
        }

    }

