package com.example.demo.repository;

import com.example.demo.model.Persona;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonaRepository extends ReactiveCrudRepository<Persona, Integer> {
}
