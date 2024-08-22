package org.example;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Random random = new Random();

        System.out.println("MONO");
        Mono<Integer> mono = Mono.just(random.nextInt(50));

        mono.subscribe(num -> System.out.println("Entrada: " + num));
        Disposable monoSuscriber1 = mono.filter(num -> num > 10).subscribe(num -> System.out.println("Suscriber 1: -> " + Math.sqrt(num)));
        Disposable monoSuscriber2 = mono.filter(num -> num > 20).subscribe(num -> System.out.println("Suscriber 2: -> " + Math.sqrt(num)));

        System.out.println("FLUX");
        Flux<String> flux = Flux.just("Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Paraguay");

        Disposable fluxSuscriber1 = flux.filter(country -> country.startsWith("C"))
                .subscribe(country -> System.out.println("Suscriber 1: Empieza por C -> " + country));
        Disposable fluxSuscriber2 = flux.filter(country -> country.length() > 5)
                .subscribe(country -> System.out.println("Suscriber 2: Longitud > 5 -> " + country + " " + country.replaceAll("[^AEIOUaeiouÁÉÍÓÚáéíóú]","")));





    }
}