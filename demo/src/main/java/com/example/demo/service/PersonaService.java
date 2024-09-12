package com.example.demo.service;


import com.example.demo.model.Persona;
import com.example.demo.repository.PersonaRepository;
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

    public Mono<Persona> createPersona(Persona persona) {
        return repository.save(persona);
    }

    public Mono<Persona> updatePersona(Long id, Persona persona) {

        return repository.findById(id)
                .flatMap(personaActualizar -> {
                    personaActualizar.setName(persona.getName());
                    personaActualizar.setAge(persona.getAge());
                    personaActualizar.setGender(persona.getGender());
                    personaActualizar.setDateOfBirth(persona.getDateOfBirth());
                    personaActualizar.setBloodType(persona.getBloodType());
                    return repository.save(personaActualizar);
                });
    }

    public Mono<Void> deletePersona(Long id) {
        return repository.deleteById(id);
    }
}
