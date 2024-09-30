package com.curso_java.tienda.repositories;

import com.curso_java.tienda.entities.OrdenCompraProducto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OrdenCompraProductoRepository extends ReactiveCrudRepository<OrdenCompraProducto, String> {
    Flux<OrdenCompraProducto> findAllByOrdenId(String id);
}

