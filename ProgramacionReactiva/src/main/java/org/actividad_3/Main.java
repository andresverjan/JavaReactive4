package org.actividad_3;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Main {
    public static void main(String[] args) {
        Mono<Integer> mono = Mono.just((int) (Math.random() * 30) + 1);
        System.out.println("Mono");
        mono.subscribe(
                element -> System.out.println("Entero recibido: " + element),
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Secuencia completada")
        );


        mono.subscribe(
                element -> {
                    if(element > 10 && element < 21){
                        System.out.println("El entero esta entre 10 y 20");
                        System.out.println("Raiz cuadrada del entero recibido: " + Math.sqrt(element));
                    }
                    },
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Secuencia numero rango 10 - 20 completada")
        );

        mono.subscribe(
                element -> {
                    if(element > 20){
                        System.out.println("El entero es mayor a 20");
                        System.out.println("Raiz cuadrada del entero recibido: " + Math.sqrt(element));
                    }
                },
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Secuencia numero mayor 20 completada")
        );

        System.out.println("Flux");
        Flux<String> flux = Flux.just("Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Paraguay");

        flux.subscribe(
                element -> {
                    if (element.toUpperCase().charAt(0) == 'C'){
                        System.out.println("Elemento recibido que empieza por C: " + element);
                    }
                },
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Secuencia paises que empiezan con C completada")
        );

        flux.subscribe(
                element -> {
                    if (element.length() > 5 ){
                        System.out.println("Elemento recibido que tiene longitud mayor a 5: "+element);
                        StringBuilder sb = new StringBuilder();

                        for (int i = 0; i < element.length(); i++) {

                            // Check if our list of vowels contains the current char. If the current char exists in the String of vowels, it will have an index of 0 or greater.
                            if ("AEIOUaeiou".indexOf(element.charAt(i)) > -1) {

                                // If so, add it to our StringBuilder
                                sb.append(element.charAt(i));
                            }
                        }
                        System.out.println("Out: "+sb);
                    }
                },
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Secuencia paises con longitud mayor a 5 completada")
        );
    }
}
