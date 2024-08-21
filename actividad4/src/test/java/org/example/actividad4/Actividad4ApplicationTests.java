package org.example.actividad4;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class Actividad4ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test(){
        Mono<String> helloMono = Mono.just("Hello");
        StepVerifier.create(helloMono)
                .expectNext("Hello")
                .expectComplete()
                .verify();
    }

    @Test
    void test2() {
        Mono<String> mono = Mono.just("Hola, Mundo!");
        // Suscribirse al Mono y procesar el elemento emitido
        mono.subscribe(element -> System.out.println("Elemento recibido: " + element),
        error -> System.err.println("Error: " + error.getMessage()),
        () -> System.out.println("Secuencia completada"));
    }

    @Test
    void test3(){
        Flux<String> flux = Flux.just("Hello", "Mateo");
        StepVerifier.create(flux)
                .expectNext("Hello")
                .expectNext("Mateo")
                .expectComplete()
                .verify();
    }

    @Test
    void test4() {
        Flux<String> flux = Flux.just("Hola", "Mundo", "!");
        flux.subscribe(
                element -> System.out.println("Elemento recibido: " + element),
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Secuencia completada")
        );
    }

}