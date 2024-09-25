package com.example.trabajoFinal.controller;

import com.example.trabajoFinal.model.Persona;
import com.example.trabajoFinal.service.PersonaService;
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

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping
    public Flux<Persona> getAll() {
        return personaService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<Persona> getPersonaId(@PathVariable Integer id) {
        return personaService.getPersonaId(id);
    }

    @PostMapping
    public Mono<Persona> save(@RequestBody Persona persona) {
        return personaService.save(persona);
    }

    @PutMapping("/{id}")
    public Mono<Persona> update(@PathVariable Integer id, @RequestBody Persona persona) {
        return personaService.update(id, persona);
    }

    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable Integer id) {
        return personaService.delete(id);
    }

}
