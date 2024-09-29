package com.curso_java.tienda.repositories;

import com.curso_java.tienda.entities.OrdenVenta;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenVentaRepository extends ReactiveCrudRepository<OrdenVenta, String> {
}

