package com.curso_java.tienda.repositories;

import com.curso_java.tienda.entities.Producto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends ReactiveCrudRepository<Producto, String> {
    // Consultas personalizadas, si es necesario
}

