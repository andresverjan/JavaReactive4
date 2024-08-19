package com.curso_java.conexion.service;

import com.curso_java.conexion.entities.Prueba;
import com.curso_java.conexion.repository.PruebRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PruebaService {

    @Autowired
    private PruebRepository repository;

    public Mono<Prueba> getEntityById(Integer id) {
        return repository.findById(id);
    }
}