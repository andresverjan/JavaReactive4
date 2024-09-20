package com.example.demo.controller;



import com.example.demo.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.example.demo.service.PersonService;

@RestController
@RequestMapping("/persons")
public class PersonController {
    @Autowired
    private PersonService personService;
    @PostMapping
    public Mono<ResponseEntity<Person>> createPerson(@RequestBody Person person) {
        return personService.createPerson(person)
                .map(savedPerson -> ResponseEntity.status(HttpStatus.CREATED).body(savedPerson));
    }
    @GetMapping
    public Flux<Person> getAllPersons() {
        return personService.getAllPersons();
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Person>> getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Person>> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        return personService.updatePerson(id, person)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deletePerson(@PathVariable Long id) {
        return personService.deletePerson(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

