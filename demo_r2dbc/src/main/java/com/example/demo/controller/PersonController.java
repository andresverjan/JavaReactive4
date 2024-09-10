package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/persons")
@AllArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping
    public Flux<Person> getPersons() {return personService.getPersons();}

    @GetMapping("/{id}")
    public Mono<Person> getPersonById(@PathVariable Long id){return personService.getPersonById(id);}

    @PostMapping
    public Mono<Person> create(@RequestBody Person p){return personService.create(p);}

    @PutMapping
    public Mono<String> update(@RequestBody Person p){return personService.update(p);}

    @DeleteMapping("/{id}")
    public Mono<Void> deletePersonById(@PathVariable Long id){
        return personService.deletePersonById(id)
                .doOnSubscribe(subscription -> System.out.println("id to be deleted: "+id))
                .doOnError(err-> System.out.println("Issues deleting the user with id: "+id))
                .then(Mono.empty());
    }


}
