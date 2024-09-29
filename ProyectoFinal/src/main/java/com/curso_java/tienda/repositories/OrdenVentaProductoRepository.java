package com.curso_java.tienda.repositories;

import com.curso_java.tienda.entities.OrdenVentaProducto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OrdenVentaProductoRepository extends ReactiveCrudRepository<OrdenVentaProducto, String> {

    @Query("SELECT * FROM ordenventaproducto WHERE orden_id = :ordenId AND producto_id = :productoId")
    Mono<OrdenVentaProducto> findByOrdenIdAndProductoId(String ordenId, String productoId);

    Flux<OrdenVentaProducto> findAllByOrdenId(String ordenId);
}

