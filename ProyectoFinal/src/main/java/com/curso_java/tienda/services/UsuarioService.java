package com.curso_java.tienda.services;


import com.curso_java.tienda.entities.Usuario;
import com.curso_java.tienda.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear o actualizar un usuario
    public Mono<Usuario> saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Obtener todos los usuarios
    public Flux<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // Obtener un usuario por ID
    public Mono<Usuario> getUsuarioById(String id) {
        return usuarioRepository.findById(id);
    }

    // Eliminar un usuario
    public Mono<Void> deleteUsuario(String id) {
        return usuarioRepository.deleteById(id);
    }
}

