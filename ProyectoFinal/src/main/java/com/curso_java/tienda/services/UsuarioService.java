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

/**
 * Servicio para gestionar usuarios.
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    /**
     * Crea o actualiza un usuario.
     *
     * @param usuario El usuario a crear o actualizar.
     * @return Un Mono que emite el DTO del usuario creado o un error si el ID del usuario ya existe.
     */
    public Mono<UsuarioDTO> saveUsuario(Usuario usuario) {
        var fechaCreacion = LocalDateTime.now();
        usuario.setCreatedAt(fechaCreacion);
        usuario.setUpdatedAt(fechaCreacion);

        return usuarioRepository.existsById(usuario.getId())
                .flatMap(existe -> {
                    if (existe) {
                        return Mono.error(new IllegalArgumentException("El ID del usuario ya existe"));
                    } else {
                        return r2dbcEntityTemplate.insert(Usuario.class).using(usuario)
                                .map(usuarioGuardado -> toUsuarioDTO(usuarioGuardado));
                    }
                });
    }

    /**
     * Obtiene todos los usuarios.
     *
     * @return Un Flux que emite los DTOs de todos los usuarios.
     */
    public Flux<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll()
                .flatMap(usuario -> Flux.just(toUsuarioDTO(usuario)));
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id El ID del usuario.
     * @return Un Mono que emite el DTO del usuario encontrado o vacío si no se encuentra.
     */
    public Mono<UsuarioDTO> getUsuarioById(String id) {
        return usuarioRepository.findById(id)
                .map(usuario -> toUsuarioDTO(usuario));
    }

    /**
     * Actualiza un usuario por su ID.
     *
     * @param id El ID del usuario a actualizar.
     * @param usuario Los datos del usuario a actualizar.
     * @return Un Mono que emite el DTO del usuario actualizado o vacío si no se encuentra.
     */
    public Mono<UsuarioDTO> updateUsuario(String id, Usuario usuario) {
        return usuarioRepository.findById(id)
                .flatMap(u -> {
                    u.setNombre(usuario.getNombre());
                    u.setEmail(usuario.getEmail());
                    u.setUpdatedAt(LocalDateTime.now());
                    return usuarioRepository.save(u);
                }).map(usuarioActualizado -> toUsuarioDTO(usuarioActualizado));
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id El ID del usuario a eliminar.
     * @return Un Mono que emite el DTO del usuario eliminado o vacío si no se encuentra.
     */
    public Mono<UsuarioDTO> deleteUsuario(String id) {
        return usuarioRepository.findById(id)
                .flatMap(usuario -> usuarioRepository.delete(usuario)
                        .then(Mono.just(toUsuarioDTO(usuario))))
                .switchIfEmpty(Mono.empty());
    }

    /**
     * Convierte un Usuario a UsuarioDTO.
     *
     * @param usuario El usuario a convertir.
     * @return El DTO del usuario.
     */
    private UsuarioDTO toUsuarioDTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getRol());
    }
}