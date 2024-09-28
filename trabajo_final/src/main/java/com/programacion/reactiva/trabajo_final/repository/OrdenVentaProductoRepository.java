package com.programacion.reactiva.trabajo_final.repository;

import com.programacion.reactiva.trabajo_final.model.OrdenVentaProducto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenVentaProductoRepository extends ReactiveCrudRepository<OrdenVentaProducto, Long> {
}
