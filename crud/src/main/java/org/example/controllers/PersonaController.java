package org.example.controllers;

import org.example.entities.Persona;
import org.example.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/personas")
public class PersonaController {

    @Autowired
    private PersonaService service;

    @GetMapping("/{id}")
    public Mono<Persona> getPersonaById(@PathVariable Long id) {
        return service.getPersonaById(id);
    }

    @GetMapping
    public Flux<Persona> getAllPersonas() {
        return service.getAllPersonas();
    }

    @PostMapping
    public Mono<Persona> create(@RequestBody Persona p){return service.addPersona(p);}

    @PutMapping
    public Mono<String> update(@RequestBody Persona p){return service.updatePersona(p);}

    @DeleteMapping("/{id}")
    public Mono<Void> deletePersonById(@PathVariable Long id){
        return service.deletePersonaById(id)
                .doOnSubscribe(subscription -> System.out.println("id to be deleted: "+id))
                .doOnError(err-> System.out.println("Issues deleting the user with id: "+id))
                .then(Mono.empty());
    }

}