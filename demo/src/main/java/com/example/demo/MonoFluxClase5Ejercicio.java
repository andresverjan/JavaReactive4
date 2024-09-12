package com.example.demo;

import reactor.core.publisher.Mono;

import java.util.Random;

public class MonoFluxClase5Ejercicio {

    public static void main(String[] args) {
        /*
        Mono<String> helloMono = Mono.just("Hello");
        StepVerifier.create(helloMono)
                .expectNext("Hello")
                .expectComplete()
                .verify();

        Mono<String> mono1 = Mono.just("hola, Mundo");
        mono1.subscribe(
                element-> System.out.println("Elemento recibido: "+element),
                error-> System.out.println("Error: "+error.getMessage()),
                ()-> System.out.println("Secuencia Completada")
        );

        Flux<String> flux = Flux.just("Hola", "Mundo", "!");
        flux.subscribe(
                element -> System.out.println("Elemento recibido: " + element),
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Secuencia completada")
        );
        */
        Random random = new Random();
        Mono<Integer> monoaleatorio = Mono.just(random.nextInt(30));

        monoaleatorio.subscribe(
                element-> {
                    System.out.println("Monoaleatorio subscribe1 elemento recibido: " + element);
                    if (element<=10){
                        System.out.println("numero menor o igual a 10");
                    }else {
                        System.out.println(element+" raiz cuadrada: "+Math.sqrt(element));
                    }
                },
                error-> System.out.println("Error: "+error.getMessage()),
                ()-> System.out.println("Secuencia Completada")
        );

        monoaleatorio.subscribe(
                element-> {
                    System.out.println("Monoaleatorio subscribe2 elemento recibido: " + element);
                    if (element<=20){
                        System.out.println("numero menor o igual a 20");
                    }else {
                        System.out.println(element+" raiz cuadrada: "+Math.sqrt(element));
                    }
                },
                error-> System.out.println("Error: "+error.getMessage()),
                ()-> System.out.println("Secuencia Completada")
        );


    }
}
