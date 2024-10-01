package com.example.EntregaFinal.repository;

import com.example.EntregaFinal.model.Producto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository  extends ReactiveCrudRepository<Producto, Long> {
}
