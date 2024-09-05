package Ejercicio6;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class EjercicioExcepciones {

    public static void main(String[] args) {
        EjercicioExcepciones ejercicio = new EjercicioExcepciones();
        System.out.println("----------onErrorResume------------");
        ejercicio.onErrorResumeExample();
        System.out.println("----------doOnError------------");
        ejercicio.doOnErrorExample();
        System.out.println("----------onErrorReturnExample------------");
        ejercicio.onErrorReturnExample();
    }

    public void onErrorResumeExample() {
        Mono<Integer> source = Mono.just("error")
                .map(Integer::parseInt)
                .onErrorResume(error -> {
                    System.out.println("Error occurred: " + error.getMessage());
                    return Mono.just(0); // Proporcionar un valor alternativo en caso de error
                });

        source.subscribe(System.out::println);
    }

    public void doOnErrorExample() {
        Flux<Integer> numbersFlux = Flux.just(1, 2, 3, 4, 5);
        Flux<Integer> transformedFlux = numbersFlux.map(number -> {
            if (number == 3) {
                throw new RuntimeException("Encountered an error processing element: " + number);
            }
            return number * 2;
        });
        transformedFlux.doOnError(error -> {
            System.err.println("An error occurred: " + error.getMessage());
        }).subscribe(
                System.out::println,
                // Handle errors emitted by the Flux
                error -> System.err.println("Error: " + error.getMessage())
        );

    }

    public void onErrorReturnExample() {
        Mono<Integer> source = Mono.just("error")
                .map(Integer::parseInt)
                .onErrorReturn(0); // Proporcionar un valor alternativo en caso de error

        source.subscribe(System.out::println);
    }

}