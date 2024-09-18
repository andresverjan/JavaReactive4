package com.reactive.crud.controllers;


import lombok.AllArgsConstructor;
import com.reactive.crud.models.Person;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.reactive.crud.service.PersonService;

@RestController
@RequestMapping("/persons")
@AllArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping
    public Flux<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public Mono<Person> getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @PostMapping
    public Mono<Person> createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @PutMapping("/{id}")
    public Mono<Person> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        return personService.updatePerson(id, person);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deletePerson(@PathVariable Long id) {
        return personService.deletePersonById(id)
                .doOnSubscribe( subscription -> System.out.println("Se ha eliminado a la persona con id: " + id))
                .doOnError(error -> System.out.println("Error eliminando la persona con id: "+id+" error: " + error.getMessage()))
                .then();
    }
}