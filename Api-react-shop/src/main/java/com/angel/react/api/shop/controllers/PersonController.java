package com.angel.react.api.shop.controllers;

import com.angel.react.api.shop.model.PersonEntity;
import com.angel.react.api.shop.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/retrieve")
    public Flux<PersonEntity> finAll(){
        return this.personService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<PersonEntity> finById(@PathVariable Long id){
        return this.personService.getById(id);
    }

    @PostMapping()
    public Mono<PersonEntity> create(@RequestBody PersonEntity person){
        return this.personService.create(person);
    }

    @PutMapping()
    public Mono<PersonEntity> update(@RequestBody PersonEntity person){
        return this.personService.update(person);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteById(@PathVariable Long id){
        return this.personService.deleteById(id);
    }
}
