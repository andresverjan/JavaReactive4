package com.curso_java.tienda.repositories;

import com.curso_java.tienda.entities.Usuario;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UsuarioRepository extends ReactiveCrudRepository<Usuario, String> {

    Mono<Usuario> findByIdAndRol(String id, String rol);
}
