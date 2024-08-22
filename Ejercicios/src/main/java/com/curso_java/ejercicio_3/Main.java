package com.curso_java.ejercicio_3;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        ejercicioMono();
        ejercicioFlux();
    }

    public static void ejercicioMono(){

        System.out.println("\nEjercicio Mono");

        Random random = new Random();
        Mono<Integer> mono = Mono.just(random.nextInt(50));

        mono.subscribe(numero -> {
            System.out.println("\nSubscriber 1: Número " + numero);
            System.out.println(numero > 10 ? "Raíz cuadrada: " + Math.sqrt(numero) : numero + " no es mayor a 10");
        });

        mono.subscribe(numero -> {
            System.out.println("\nSubscriber 2: Número " + numero);
            System.out.println(numero > 20 ? "Raíz cuadrada: " + Math.sqrt(numero) : numero + " no es mayor a 10");
        });
    }

    public static void ejercicioFlux(){

        System.out.println("\nEjercicio Flux");

        Flux<String> paises = Flux.just("Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Paraguay");

        System.out.println("\nSubscriber 1");
        paises.subscribe(pais -> {
            if (pais.startsWith("C") || pais.startsWith("c")) {
                System.out.println(pais);
            }
        });

        System.out.println("\nSubscriber 2");
        paises.subscribe(pais -> {
            if (pais.length() > 5) {
                String vowels = pais.replaceAll("[^AEIOUaeiouÁÉÍÓÚáéíóú]", "");
                System.out.println(vowels);
            }
        });
    }
}
