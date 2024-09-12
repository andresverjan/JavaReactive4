package com.curso_java.conexion.controllers;

import com.curso_java.conexion.entities.Persona;
import com.curso_java.conexion.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/create")
    public Mono<Persona> createPersona(@RequestBody Persona persona) {
        return service.createPersona(persona);
    }

    @PutMapping("/update/{id}")
    public Mono<Persona> updatePersona(@PathVariable Long id, @RequestBody Persona persona) {
        return service.updatePersona(id, persona);
    }

    @DeleteMapping("delete/{id}")
    public Mono<Void> deletePersona(@PathVariable Long id) {
        return service.deletePersona(id);
    }
}