package com.example.RestReact.controllers;

import com.example.RestReact.model.PersonEntity;
import com.example.RestReact.service.PersonService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/persona")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/list")
    public Flux<PersonEntity> finById(){
        return this.personService.getAll();
    }

    @GetMapping("/id/{id}")
    public Mono<PersonEntity> finById(@PathVariable Long id){
        return this.personService.getById(id);
    }

    @PostMapping
    public Mono<PersonEntity> create(@RequestBody PersonEntity person){
        return this.personService.create(person);
    }

    @DeleteMapping("/id/{id}")
    public Mono<PersonEntity> deleteById(@PathVariable Long id){
        return this.personService.deleteById(id)
                .doOnSubscribe(s -> System.out.println(" id eliminada"))
                .doOnError(e -> System.out.println("error"))
                .then(Mono.empty());
    }
}
