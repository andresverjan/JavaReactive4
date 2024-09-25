package com.example.trabajoFinal.repository;

import com.example.trabajoFinal.model.OrdenesVentas;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OrdenesVentasRepository extends ReactiveCrudRepository<OrdenesVentas, Integer> {

    Flux<OrdenesVentas> findByProductId(int id);
}
