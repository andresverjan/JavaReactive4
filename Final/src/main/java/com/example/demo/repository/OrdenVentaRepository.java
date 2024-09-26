package com.example.demo.repository;

import com.example.demo.model.OrdenVenta;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OrdenVentaRepository extends ReactiveCrudRepository<OrdenVenta, Long> {
    Flux<OrdenVenta> findAllByUserId(Long userId);

}