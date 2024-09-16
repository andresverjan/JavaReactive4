package org.example.repository;

import org.example.entities.Persona;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends ReactiveCrudRepository<Persona, Long> {
}