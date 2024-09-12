package org.example;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.*;
import java.util.stream.Collectors;

public class MonoFluxEjercicio {
    public static void main(String[] args) {

        Random random = new Random();
        Mono<Integer> monoaleatorio = Mono.just(random.nextInt(30));
        Flux<String> flux = Flux.just("Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Paraguay", "China", "Croacia", "Polonia", "Alemania", "Italia", "Francia");

        ///MONO Suscribes
        monoaleatorio.subscribe(
                element-> {
                    System.out.println("Monoaleatorio subscribe1 elemento recibido: " + element);
                    if (element> 10 && element <= 20){
                        System.out.println(element+" raiz cuadrada: "+Math.sqrt(element));
                    }else {
                        System.out.println("numero no esta en el rango mayor de 10 y menor a 20");
                    }
                },
                error-> System.out.println("Error: "+error.getMessage()),
                ()-> System.out.println("Mono subscribe 1 complete")
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
                ()-> System.out.println("Mono subscribe2 complete")
        );

        /// FLUX Suscribes
        flux.subscribe(
                element -> {

                    if ( String.valueOf(element.charAt(0)).toUpperCase().equals("C")){
                        System.out.println("Suscribe 1 elemento recibido: " + element);
                    }


                },
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Flex Subscribe 1 complete")
        );

        flux.subscribe(
                element -> {

                    if ( element.length()>5){
                        //System.out.println("Suscribe 2 elemento recibido: " + element);
                        String vocalesEnPais = element.chars()
                                .mapToObj(c->(char) c)
                                .filter(c->"AEIOUaeiou".indexOf(c) != -1)
                                .map(String::valueOf)
                                .collect(Collectors.joining());
                        System.out.println("Suscribe 2 elemento recibido: "+element+" Vocales :"+vocalesEnPais);
                    }


                },
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Flex Subscribe 2 complete")
        );

        // Crear un Mono que emite el valor actual del sistema cuando se suscribe
        //Mono.fromCallable(() -> UUID.randomUUID().toString())
                //.subscribeOn(Schedulers.boundedElastic());


        //Mono<String> deferMono2 = Mono.fromCallable(()->);




    }

}