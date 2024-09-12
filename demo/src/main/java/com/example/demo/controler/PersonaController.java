package com.example.demo.controler;

import com.example.demo.model.Persona;
import com.example.demo.service.PersonaService;
import jakarta.validation.Valid;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/create")
    public Mono<Persona> createPersona(@Valid @RequestBody Persona persona) {
        return service.createPersona(persona);
    }

    @PutMapping("/update/{id}")
    public Mono<Persona> updatePersona(@PathVariable Long id, @RequestBody Persona persona) {
        return service.getPersonaById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Persona no encontrada con el ID: " + id)))
                .flatMap(existingPersona -> service.updatePersona(id, persona));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deletePersona(@PathVariable Long id) {
        return service.getPersonaById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Persona no encontrada con el ID: " + id)))
                .flatMap(persona -> service.deletePersona(id));
    }
}