package com.valko.actividad4.controllers;

import lombok.AllArgsConstructor;
import com.valko.actividad4.models.Person;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import com.valko.actividad4.service.PersonService;

@RestController
@RequestMapping("/persons")
@AllArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping("/{id}")
    public Mono<Person> getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    /*@PostMapping
    public Mono<Person> createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @PutMapping
    public Mono<Person> updatePerson(@RequestBody Person person) {
        return personService.updatePerson(person);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deletePerson(@PathVariable Long id) {
        return personService.deletePersonById(id)
                .doOnSubscribe( subscription -> System.out.println("Id to be deleted: " + id))
                .then();
    }*/
}
