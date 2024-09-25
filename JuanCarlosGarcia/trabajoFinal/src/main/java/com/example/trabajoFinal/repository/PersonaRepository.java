package com.example.trabajoFinal.repository;

import com.example.trabajoFinal.model.Persona;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonaRepository extends ReactiveCrudRepository<Persona, Integer> {
}
