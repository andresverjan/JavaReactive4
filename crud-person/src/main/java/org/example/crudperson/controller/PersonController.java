package org.example.crudperson.controller;

import io.netty.util.internal.shaded.org.jctools.queues.MpmcArrayQueue;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.crudperson.exceptions.NotFoundException;
import org.example.crudperson.model.Person;
import org.example.crudperson.service.PersonService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {

    private PersonService personService;

    @GetMapping
    public Mono<List<Person>> getPerson() {
        return personService.getPerson();
    }

    @PostMapping
    public Mono<String> savePerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }

    @GetMapping("/{id}")
    public Mono<Person> findById(@PathVariable Long id) {
        return personService.findById(id);
    }

    @DeleteMapping("/{id}")
    public Mono<String> deleteById(@PathVariable Long id) {
        return personService.deleteById(id);
    }

    @PutMapping
    public Mono<String> updatePerson(@RequestBody Person person) {
        return personService.updatePerson(person);
    }


}
