package com.angel.reactividad.monoyflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

import java.util.Random;

@SpringBootApplication
public class MonoExample {

    public static void main(String[] args) {
        SpringApplication.run(MonoExample.class, args);

        Mono<Integer> mono = Mono.just(new Random().nextInt(100)+1);

        mono.subscribe(
                element -> {
                    String result = element > 10 ? element + " es mayor a 10, raíz: "+ Math.sqrt(element) : "Elemento:" + element;
                    System.out.println(result);
                },
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Secuencia completada subscriber 1")
        );

        mono.subscribe(
                element -> {
                    String result = element > 20 ? element + " es mayor a 20, raíz: "+ Math.sqrt(element) : "Elemento:" + element;
                    System.out.println(result);
                },
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Secuencia completada subscriber 2")
        );
    }
}
