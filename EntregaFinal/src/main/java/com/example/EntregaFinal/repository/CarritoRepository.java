package com.example.EntregaFinal.repository;

import com.example.EntregaFinal.model.Carrito;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoRepository extends ReactiveCrudRepository<Carrito, Long> {
}
