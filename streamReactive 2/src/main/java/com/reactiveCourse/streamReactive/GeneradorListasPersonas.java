package com.reactiveCourse.streamReactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GeneradorListasPersonas {
    public Mono<Persona> agregarPersona(Persona persona);
    public Flux<Persona> obtenerPersonas(String peek);
    public void crearListaPersonas();
    public Flux<Persona> obtenerPersonasPorEdad(int edad);
    public Flux<Persona> obtenerPersonasPorSigno(String signo);
    public Mono<Persona> obtenerPersonasPorTelefono(String telefono);
    public Mono<Persona> eliminarPersona(Persona persona);
}
