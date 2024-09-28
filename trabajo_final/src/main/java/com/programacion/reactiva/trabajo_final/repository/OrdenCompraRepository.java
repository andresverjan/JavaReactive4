package com.programacion.reactiva.trabajo_final.repository;

import com.programacion.reactiva.trabajo_final.model.OrdenCompra;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenCompraRepository extends ReactiveCrudRepository<OrdenCompra, Long> {
}
