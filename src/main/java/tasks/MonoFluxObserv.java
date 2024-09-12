package tasks;

import java.util.List;

public class MonoFluxObserv {
    public static void main(String[] args) {
        //Tarea 2: Ejercicio 1
        Mono<Integer> randomMono = Mono.fromSupplier(() -> new Random().nextInt(100));

        // Subscriber 1
        randomMono.subscribe(number -> {
            if (number > 10) {
                System.out.println("Subscriber 1: La raíz cuadrada de " + number + " es " + Math.sqrt(number));
            } else {
                System.out.println("Subscriber 1: Número menor o igual a 10, no se calcula la raíz cuadrada.");
            }
        });

        // Subscriber 2
        randomMono.subscribe(number -> {
            if (number > 20) {
                System.out.println("Subscriber 2: La raíz cuadrada de " + number + " es " + Math.sqrt(number));
            } else {
                System.out.println("Subscriber 2: Número menor o igual a 20, no se calcula la raíz cuadrada.");
            }
        });


        //Tarea 2: Ejercicio 2

        List<String> countries = List.of("Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Paraguay");

        Flux<String> countryFlux = Flux.fromIterable(countries);

        // Subscriber 1
        countryFlux.filter(country -> country.startsWith("C"))
                .subscribe(country -> System.out.println("Subscriber 1: País que comienza con C: " + country));

        // Subscriber 2
        countryFlux.filter(country -> country.length() > 5)
                .subscribe(country -> {
                    String vowels = country.replaceAll("[^AEIOUaeiou]", "");
                    System.out.println("Subscriber 2: Vocales en el país " + country + ": " + vowels);
                });
    }

    }
}
