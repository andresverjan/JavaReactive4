package com.excercise3.test_r2dbc.controllers;
import com.excercise3.test_r2dbc.entities.Person;
import com.excercise3.test_r2dbc.services.PersonService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

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
        return personService.deletePerson(id);
    }
}
