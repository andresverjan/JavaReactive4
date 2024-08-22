package com.example.demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MonoFluxTest {

    @Test
    void testMonoWithMultipleSubscribers() {
        Mono<Integer> randomMono = Mono.fromSupplier(() -> new Random().nextInt(100)); // Número aleatorio entre 0 y 99

        randomMono.subscribe(number -> {
            if (number > 10) {
                System.out.println("Suscriptor 1 - Raíz cuadrada del número " + number + " > 10: " + Math.sqrt(number));
            } else {
                System.out.println("Suscriptor 1 - Número " + number + " menor o igual a 10: " + number);
            }
        });

        randomMono.subscribe(number -> {
            if (number > 20) {
                System.out.println("Suscriptor 2 - Raíz cuadrada del número " + number + " > 20: " + Math.sqrt(number));
            } else {
                System.out.println("Suscriptor 2 - Número " + number + " menor o igual a 20: " + number);
            }
        });
    }

    @Test
    void testFluxWithMultipleSubscribers() {
        List<String> countries = Arrays.asList("Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Paraguay");

        Flux<String> countryFlux = Flux.fromIterable(countries);

        countryFlux.subscribe(country -> {
            if (country.startsWith("C")) {
                System.out.println("Suscriptor 1 - País que empieza con C: " + country);
            }
        });

        countryFlux.subscribe(country -> {
            if (country.length() > 5) {
                StringBuilder vowels = new StringBuilder();
                for (char c : country.toCharArray()) {
                    if (isVowel(c)) {
                        vowels.append(c);
                    }
                }
                System.out.println("Suscriptor 2 - Vocales del país con nombre " + country + " > 5 caracteres: " + vowels);
            }
        });
    }

    private boolean isVowel(char c) {
        return "AEIOUaeiou".indexOf(c) != -1;
    }

}
