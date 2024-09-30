package com.curso_java.tienda.repositories;

import com.curso_java.tienda.entities.VendedorEmpresa;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendedorEmpresaRepository extends ReactiveCrudRepository<VendedorEmpresa, String> {
}

