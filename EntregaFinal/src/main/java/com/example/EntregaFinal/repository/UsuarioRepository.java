package com.example.EntregaFinal.repository;

import com.example.EntregaFinal.model.Usuario;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends ReactiveCrudRepository<Usuario, Long> {
}
