package ejemplos;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Ejemplo6ManejoErrores {
    public static void main(String[] args) {

        Mono<Integer> source = Mono.just("error")
                .map(Integer::parseInt)
                .onErrorResume(error -> {
                    System.out.println("Error occurred: " + error.getMessage());
                    return Mono.just(0); // Proporcionar un valor alternativo en caso de error
                });

        //source.subscribe(System.out::println);


        /*public Mono<ServerResponse> handleWithErrorReturn(ServerRequest request) {
            return sayHello(request)
                    .onErrorReturn("Hello Stranger")
                    .flatMap(s -> ServerResponse.ok()
                            .contentType(MediaType.TEXT_PLAIN)
                            .bodyValue(s));
        }*/

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

        Flux<String> errorFlux = Flux.error(new RuntimeException("Simulated error"));
    }
}
