package com.curso.java.demo.controllers;

import com.curso.java.demo.model.Person;
import com.curso.java.demo.service.PersonService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping
    public Flux<Person> getAllPeople(){
        return personService.getAllPeople();
    }

    @GetMapping("/{id}")
    public Mono<Person> getPersonById(@PathVariable Long id){
        return personService.getPersonById(id);
    }

    @PostMapping
    public Mono<Person> createPerson(@RequestBody Person person){
        return personService.createPerson(person);
    }

    @PostMapping("/{id}")
    public Mono<Person> updatePerson(@PathVariable Long id, @RequestBody Person person){
        return personService.updatePerson(id, person);
    }

    @DeleteMapping("/{id}")
    public Mono<String> deletePerson(@PathVariable Long id){
        return personService.deletePerson(id);
    }





}
