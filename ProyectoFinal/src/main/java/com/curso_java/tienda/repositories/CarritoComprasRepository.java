package com.curso_java.tienda.repositories;

import com.curso_java.tienda.entities.CarritoCompra;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoComprasRepository extends ReactiveCrudRepository<CarritoCompra, String> {
    // Consultas personalizadas, si es necesario
}

