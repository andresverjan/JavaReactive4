package com.example.trabajoFinal.repository;

import com.example.trabajoFinal.model.CarritosCompras;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CarritoComprasRepository extends ReactiveCrudRepository<CarritosCompras, Integer> {

    Mono<CarritosCompras> findByProductIdAndPersonaId(int productId, int personaId);
    Flux<CarritosCompras> findByPersonaId(int personaId);


}
