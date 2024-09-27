package com.curso_java.tienda.repositories;

import com.curso_java.tienda.entities.Usuario;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends ReactiveCrudRepository<Usuario, String> {
    // Puedes agregar consultas personalizadas si es necesario
}

