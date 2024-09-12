package com.example.demo.service;

import com.example.demo.model.Persona;
import com.example.demo.repository.PersonaRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonaService {
    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public Flux<Persona> getAll() {
        return personaRepository.findAll();
    }

    public Mono<Persona> getPersonaId(Integer id) {
        return personaRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("Persona no encontrada")));
    }

    public Mono<Persona> save(Persona persona) {
        return personaRepository.save(persona);
    }

    public Mono<Persona> update(Integer id, Persona persona) {
        return personaRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("Persona no encontrada")))
                .flatMap(existingPersona -> {
                    existingPersona.setPrimerNombre(persona.getPrimerNombre());
                    existingPersona.setSegundoNombre(persona.getSegundoNombre());
                    existingPersona.setPrimerApellido(persona.getPrimerApellido());
                    existingPersona.setSegundoApellido(persona.getSegundoApellido());
                    existingPersona.setFechaNac(persona.getFechaNac());
                    return personaRepository.save(existingPersona);
                })
                .doOnError(error -> System.out.println("Error: " + error.getMessage()));
    }

    public Mono<String> delete(Integer id) {
        return personaRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("Persona no encontrada")))
                .flatMap(persona -> personaRepository.delete(persona)
                        .then(Mono.just("Persona eliminada con éxito")));
    }

}
