package com.curso_java.conexion.repository;

import com.curso_java.conexion.entities.Prueba;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PruebRepository extends ReactiveCrudRepository<Prueba, Integer> {
}