package org.example.monoflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Flow;
import java.util.stream.Collectors;

@SpringBootApplication
public class MonoFluxApplication {

    public static void main(String[] args) {

        Random rand = new Random();

        // Generate random integers in range 0 to 999

        Mono<Integer> integerMono = Mono.fromSupplier(() -> new Random().nextInt(50));

        integerMono.subscribe(
            element -> System.out.println( element > 10 ?
                    "Suscriber 1: La raiz cuadrada del numero " + element + " es -> " + Math.sqrt(element) :
                    "Suscriber 1: El numero " + element + " es menor que 10 no es posible calcular la raiz cuadrada"),
            error -> System.err.println("Error Suscriber 1: " + error.getMessage()),
            () -> System.out.println("Suscriber 1 completada")
        );

        integerMono.subscribe(
                element -> System.out.println( element > 20 ?
                        "Suscriber 2: La raiz cuadrada del numero " + element + " es -> " + Math.sqrt(element) :
                        "Suscriber 2: El numero " + element + " es menor que 20 no es posible calcular la raiz cuadrada"),
                error -> System.err.println("Error Suscriber 2: " + error.getMessage()),
                () -> System.out.println("Suscriber 2 completada")
        );


        Flux<String> stringFlux = Flux.just("Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Paraguay");

        stringFlux.subscribe(
                country -> System.out.println(country.startsWith("C") ?
                        "Suscriber 1: El país " + country + " comienza con la letra 'C'" :
                        "Suscriber 1: El país " + country + " no comienza con la letra 'C'"),
                error -> System.err.println("Error Suscriber 1: " + error.getMessage()),
                () -> System.out.println("Suscriber 1 completada")
        );



        stringFlux.subscribe(
                country -> System.out.println(country.length() > 5 ?
                        "Suscriber 2: El país " + country + " tiene las siguientes vocales " + getVowels(country) :
                        "Suscriber 2: El país " + country + " no supera 5 caracteres"),
                error -> System.err.println("Error Suscriber 2: " + error.getMessage()),
                () -> System.out.println("Suscriber 2 completada")
        );


        SpringApplication.run(MonoFluxApplication.class, args);
    }


    private static String getVowels(String country) {
        StringBuilder vowels = new StringBuilder();
        char[] charArray = country.toUpperCase().toCharArray();
        for (char c: charArray) {
            if("AEIOU".indexOf(c) != -1) {
                vowels.append(c);
            }
        }
        return vowels.toString();
    }


}
