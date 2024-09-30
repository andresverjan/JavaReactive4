package com.curso_java.tienda.repositories;

import com.curso_java.tienda.entities.Empresa;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends ReactiveCrudRepository<Empresa, String> {
}
