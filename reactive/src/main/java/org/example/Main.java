package org.example;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Main {
    public static void main(String[] args) {

        Mono<String> helloMono = Mono.just("Hello");
        StepVerifier.create(helloMono)
                .expectNext("Hello")
                .expectComplete()
                .verify();

        System.out.println("MONO");
        Mono<String> mono = Mono.just("Hola, Mundo!");        // Suscribirse al Mono y procesar el elemento emitido
        mono.subscribe(
                element -> System.out.println("Elemento recibido: " + element),
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Secuencia completada"));


// Flux
        System.out.println("FLUX");
        Flux<String> stringFlux = Flux.just("Hello", "Baeldung");
        StepVerifier.create(stringFlux)
                .expectNext("Hello")
                .expectNext("Baeldung")
                .expectComplete()
                .verify();

        Flux<String> flux = Flux.just("Hola", "Mundo", "!");
        flux.subscribe(
                element -> System.out.println("Elemento recibido: " + element),
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Secuencia completada")
        );




    }
}