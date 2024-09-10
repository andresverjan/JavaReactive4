package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public Flux<Person> getPersons(){
        return personRepository.findAll()
                .doOnNext(person -> System.out.println("Data: "+ person))
                .doOnError(System.err::println);
    }

    public Mono<Person> getPersonById(Long id){
        if(id==null){
            return Mono.empty();
        }
        return personRepository.findById(id)
                .doOnNext(person -> System.out.println("Data get:  "+person));
    }

    public Mono<Person> create(Person person){
        return personRepository.save(person);
    }

    public Mono<String> update(Person person){
        if(person.getId() != null){
            return personRepository.save(person)
                    .doOnNext(person1 -> System.out.println("Data updating: "+person1))
                    .then(Mono.just("User Updated"));
        }
        return Mono.just("User is not present");
    }

    public Mono<Void> deletePersonById(Long id){
        if(id == null){
            return Mono.error(new IllegalArgumentException("ID cannot be null"));
        }
        return personRepository.deleteById(id)
                .doOnNext(unused -> System.out.println("Data delete "+id));
    }
}
