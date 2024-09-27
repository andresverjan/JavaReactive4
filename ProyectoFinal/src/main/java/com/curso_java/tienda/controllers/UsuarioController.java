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

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseData<UsuarioDTO>> createUsuario(@RequestBody Usuario usuario) {
        return usuarioService.saveUsuario(usuario)
                .map(usuarioGuardado -> new ResponseData<>("Usuario creado exitosamente", usuarioGuardado));
    }

    @GetMapping
    public Flux<ResponseData<UsuarioDTO>> getAllUsuarios() {
        return usuarioService.getAllUsuarios()
                .map(usuario -> new ResponseData<>("Usuario encontrado", usuario));
    }

    @GetMapping("/{id}")
    public Mono<ResponseData<UsuarioDTO>> getUsuarioById(@PathVariable String id) {
        return usuarioService.getUsuarioById(id)
                .map(usuario -> new ResponseData<>("Usuario encontrado", usuario))
                .defaultIfEmpty(new ResponseData<>("Usuario no encontrado", null));
    }

    @PutMapping("/{id}")
    public Mono<ResponseData<UsuarioDTO>> updateProducto(@PathVariable String id, @RequestBody Usuario usuario) {
        return usuarioService.updateUsuario(id, usuario)
                .map(usuarioActualizado -> new ResponseData<>("Usuario actualizado exitosamente", usuarioActualizado))
                .defaultIfEmpty(new ResponseData<>("Usuario no encontrado", null));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseData<UsuarioDTO>> deleteUsuario(@PathVariable String id) {
        return usuarioService.deleteUsuario(id)
                .map(usuario -> new ResponseData<>("Usuario eliminado exitosamente", usuario))
                .defaultIfEmpty(new ResponseData<>("Usuario no encontrado", null));
    }
}