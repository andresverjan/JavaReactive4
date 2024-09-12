package com.example.demo.controllers;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;
    @GetMapping("/{id}")
    public Mono<Person> getPersonById(@PathVariable Long id){return personService.getPersonById(id);}

    @PostMapping
    public Mono<Person> create(@RequestBody Person p){return personService.createPerson(p);}

    @GetMapping("/")
    public Flux<Person> getPersons(){return personService.getPersons();}

    @PutMapping
    public Mono<String> update(@RequestBody Person p){return personService.updatePerson(p);}

    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable Long id){
        return personService.deletePersonById(id)
                .doOnSubscribe(subscription -> System.out.println("id a eliminar: "+id))
                .doOnError(e1->{
                    System.out.println("Error log: "+e1);
                });

    }

}
