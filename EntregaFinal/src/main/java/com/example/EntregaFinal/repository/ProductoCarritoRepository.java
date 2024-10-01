package com.example.EntregaFinal.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoCarritoRepository extends ReactiveCrudRepository<ProductoCarritoRepository, Long> {
}
