package com.programacion.reactiva.trabajo_final.repository;

import com.programacion.reactiva.trabajo_final.model.OrdenVenta;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenVentaRepository extends ReactiveCrudRepository<OrdenVenta, Long> {
}
