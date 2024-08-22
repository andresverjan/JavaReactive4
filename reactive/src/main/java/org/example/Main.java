package org.example;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Main {
    public static void main(String[] args) {

/*
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


*/


        System.out.println("MONO");
        Mono<Integer> mono = Mono.just(1);

        Disposable monoSuscriber1 = mono.subscribe(num -> System.out.println("Suscriber 1: Multiplica x 2 -> " + num * 2));
        Disposable monoSuscriber2 = mono.subscribe(num -> System.out.println("Suscriber 2: Multiplica x 100 -> " + num * 100));
        Disposable monoSuscriber3 = mono.subscribe(num -> System.out.println("Suscriber 3: Suma 50 -> " + num + 50));

        System.out.println("FLUX");
        Flux<Integer> flux = Flux.just(2,8,90,75,748,8,2356);

        Disposable fluxSuscriber1 = flux.subscribe(num -> System.out.println("Suscriber 1: Multiplica x 2 -> " + num * 2));
        Disposable fluxSuscriber2 = flux.subscribe(num -> System.out.println("Suscriber 2: Multiplica x 100 -> " + num * 100));
        Disposable fluxSuscriber3 = flux.subscribe(num -> System.out.println("Suscriber 3: Suma 50 -> " + num + 50));


    }
}