package com.curso_java.tienda.services;

import com.curso_java.tienda.dtos.UsuarioDTO;
import com.curso_java.tienda.entities.Usuario;
import com.curso_java.tienda.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;


    // Crear o actualizar un usuario
    public Mono<UsuarioDTO> saveUsuario(Usuario usuario) {
        var fechaCreacion= LocalDateTime.now();
        usuario.setCreatedAt(fechaCreacion);
        usuario.setUpdatedAt(fechaCreacion);

        return usuarioRepository.existsById(usuario.getId())
                .flatMap(existe -> {
                    if (existe) {
                        return Mono.error(new IllegalArgumentException("El ID del usuario ya existe"));
                    } else {
                        return r2dbcEntityTemplate.insert(Usuario.class).using(usuario).map(usuarioGuardado -> toUsuarioDTO(usuarioGuardado)); // Devuelve el DTO del usuario guardado // Realiza la inserción
                    }
                });
    }

    // Obtener todos los usuarios
    public Flux<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll().flatMap(usuario -> Flux.just(toUsuarioDTO(usuario))); // Devuelve un Flux de DTOs de usuarios
    }

    // Obtener un usuario por ID
    public Mono<UsuarioDTO> getUsuarioById(String id) {
        return usuarioRepository.findById(id).map(usuario -> toUsuarioDTO(usuario)); // Devuelve el DTO del usuario encontrado
    }

    // Actualizar un producto
    public Mono<UsuarioDTO> updateUsuario(String id, Usuario usuario) {
        return usuarioRepository.findById(id)
                .flatMap(u -> {
                    u.setNombre(usuario.getNombre());
                    u.setEmail(usuario.getEmail());
                    u.setUpdatedAt(LocalDateTime.now());
                    return usuarioRepository.save(u);
                }).map(usuarioActualizado -> toUsuarioDTO(usuarioActualizado)); // Devuelve el DTO del usuario actualizado
    }

    // Eliminar un usuario
    public Mono<UsuarioDTO> deleteUsuario(String id) {
        return usuarioRepository.findById(id)
                .flatMap(
                        usuario -> usuarioRepository.delete(usuario)
                        .then(Mono.just(toUsuarioDTO(usuario))
                ) // Devuelve el DTO del usuario eliminado
                .switchIfEmpty(Mono.empty())); // No devuelve nada si no se encuentra el usuario
    }

    // Convertir un Usuario a UsuarioDTO
    private UsuarioDTO toUsuarioDTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getRol());
    }
}