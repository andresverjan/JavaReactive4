package com.excercise3.test_r2dbc.drivenAdapters.postgres;

import com.excercise3.test_r2dbc.drivenAdapters.postgres.iPostgres.ClasesRepository;
import com.excercise3.test_r2dbc.entities.Clases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ConnectionDb {
    private final ClasesRepository repository;
    @Autowired
    public ConnectionDb(ClasesRepository repository) {
        this.repository = repository;
    }
    public Mono<Clases> findById(Long id) {
        return repository.findById(id);
    }

    public Flux<Clases> findAll() {
        return repository.findAll();
    }

}
