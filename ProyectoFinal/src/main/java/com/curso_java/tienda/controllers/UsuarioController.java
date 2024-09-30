package com.curso_java.tienda.controllers;

import com.curso_java.tienda.dtos.ResponseData;
import com.curso_java.tienda.dtos.UsuarioDTO;
import com.curso_java.tienda.entities.Usuario;
import com.curso_java.tienda.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para gestionar usuarios.
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Crea un nuevo usuario.
     *
     * @param usuario El usuario a crear.
     * @return Un Mono que emite la respuesta con el usuario creado.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseData<UsuarioDTO>> createUsuario(@RequestBody Usuario usuario) {
        return usuarioService.saveUsuario(usuario)
                .map(usuarioGuardado -> new ResponseData<>("Usuario creado exitosamente", usuarioGuardado));
    }

    /**
     * Obtiene todos los usuarios.
     *
     * @return Un Flux que emite la respuesta con la lista de todos los usuarios.
     */
    @GetMapping
    public Flux<ResponseData<UsuarioDTO>> getAllUsuarios() {
        return usuarioService.getAllUsuarios()
                .map(usuario -> new ResponseData<>("Usuario encontrado", usuario));
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id El ID del usuario.
     * @return Un Mono que emite la respuesta con el usuario correspondiente al ID proporcionado o un mensaje de error si no se encuentra.
     */
    @GetMapping("/{id}")
    public Mono<ResponseData<UsuarioDTO>> getUsuarioById(@PathVariable String id) {
        return usuarioService.getUsuarioById(id)
                .map(usuario -> new ResponseData<>("Usuario encontrado", usuario))
                .defaultIfEmpty(new ResponseData<>("Usuario no encontrado", null));
    }

    /**
     * Actualiza un usuario por su ID.
     *
     * @param id El ID del usuario a actualizar.
     * @param usuario Los datos del usuario a actualizar.
     * @return Un Mono que emite la respuesta con el usuario actualizado o un mensaje de error si no se encuentra.
     */
    @PutMapping("/{id}")
    public Mono<ResponseData<UsuarioDTO>> updateProducto(@PathVariable String id, @RequestBody Usuario usuario) {
        return usuarioService.updateUsuario(id, usuario)
                .map(usuarioActualizado -> new ResponseData<>("Usuario actualizado exitosamente", usuarioActualizado))
                .defaultIfEmpty(new ResponseData<>("Usuario no encontrado", null));
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id El ID del usuario a eliminar.
     * @return Un Mono que emite la respuesta con el usuario eliminado o un mensaje de error si no se encuentra.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseData<UsuarioDTO>> deleteUsuario(@PathVariable String id) {
        return usuarioService.deleteUsuario(id)
                .map(usuario -> new ResponseData<>("Usuario eliminado exitosamente", usuario))
                .defaultIfEmpty(new ResponseData<>("Usuario no encontrado", null));
    }
}