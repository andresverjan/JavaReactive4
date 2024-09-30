package com.curso_java.tienda.repositories;

import com.curso_java.tienda.entities.OrdenCompra;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenCompraRepository extends ReactiveCrudRepository<OrdenCompra, String> {
}
