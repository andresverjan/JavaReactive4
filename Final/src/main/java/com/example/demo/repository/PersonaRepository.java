package com.example.demo.repository;

import com.example.demo.model.Persona;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends ReactiveCrudRepository<Persona, Long> {
}
