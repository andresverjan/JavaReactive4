package com.curso_java.conexion.service;

import com.curso_java.conexion.entities.Persona;
import com.curso_java.conexion.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository repository;

    public Mono<Persona> getPersonaById(Long id) {
        return repository.findById(id);
    }

    public Flux<Persona> getAllPersonas() {
        return repository.findAll();
    }
}