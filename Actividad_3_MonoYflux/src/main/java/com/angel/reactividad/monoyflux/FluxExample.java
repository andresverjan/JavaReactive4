package com.angel.reactividad.monoyflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class FluxExample {

    public static void main(String[] args) {
        SpringApplication.run(FluxExample.class, args);

        Flux<String> flux = Flux.just("Argentina","Bolivia","Brasil","Chile","Colombia","Paraguay");
        flux.subscribe(
                element -> {if(element.charAt(0) == 'C') System.out.println(element);},
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Secuencia completada subscriber 1")
        );

        flux.subscribe(
                element -> {if(element.length() > 5) System.out.println(element.replaceAll("[^aeiouAEIOU\\W]", " "));},
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Secuencia completada subscriber 2")
        );
    }
}
