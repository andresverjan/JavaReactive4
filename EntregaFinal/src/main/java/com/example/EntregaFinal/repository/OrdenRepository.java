package com.example.EntregaFinal.repository;

import com.example.EntregaFinal.model.Orden;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface OrdenRepository extends ReactiveCrudRepository<Orden, Long> {

    Flux<Orden> findByFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

}
