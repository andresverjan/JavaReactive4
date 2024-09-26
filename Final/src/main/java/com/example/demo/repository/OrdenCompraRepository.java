package com.example.demo.repository;

import com.example.demo.model.Carrito;
import com.example.demo.model.OrdenCompra;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenCompraRepository extends ReactiveCrudRepository<OrdenCompra, Long> {
}
