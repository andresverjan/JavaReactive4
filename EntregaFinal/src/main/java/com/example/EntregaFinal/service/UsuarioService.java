package com.example.EntregaFinal.service;

import com.example.EntregaFinal.model.Carrito;
import com.example.EntregaFinal.model.Usuario;
import com.example.EntregaFinal.repository.CarritoRepository;
import com.example.EntregaFinal.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final CarritoRepository carritoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, CarritoRepository carritoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.carritoRepository = carritoRepository;
    }

    // Crear un nuevo usuario
    public Mono<Usuario> crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Buscar usuario por ID
    public Mono<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Obtener el carrito del usuario mediante su carritoId
    public Mono<Carrito> obtenerCarritoDeUsuario(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .flatMap(usuario -> {
                    Long carritoId = usuario.getCarritoId();
                    if (carritoId != null) {
                        return carritoRepository.findById(carritoId);
                    } else {
                        return Mono.error(new RuntimeException("El usuario no tiene un carrito asignado"));
                    }
                });
    }

}
