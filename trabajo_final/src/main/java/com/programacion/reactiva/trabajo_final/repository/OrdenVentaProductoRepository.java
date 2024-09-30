package com.programacion.reactiva.trabajo_final.repository;

import com.programacion.reactiva.trabajo_final.model.OrdenVentaProducto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface OrdenVentaProductoRepository extends ReactiveCrudRepository<OrdenVentaProducto, Long> {
    Flux<OrdenVentaProducto> findByOrdenVentaId(Long id);
    Flux<OrdenVentaProducto> findByProductoId(Long id);
}
