package com.curso_java.tienda.repositories;

import com.curso_java.tienda.entities.CarritoProducto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CarritoProductoRepository extends ReactiveCrudRepository<CarritoProducto, String> {

    @Query("SELECT * FROM carritoproducto WHERE carrito_id = :carritoId AND producto_id = :productoId")
    Mono<CarritoProducto> findByCarritoIdAndProductoId(String carritoId, String productoId);

    @Query("SELECT * FROM carritoproducto WHERE carrito_id = :carritoId")
    Flux<CarritoProducto> findAllByCarritoId(String carritoId);
}

