package org.example.Clase6;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import java.util.Random;


public class Clase6 {
    /*
    public static void main(String[] args) {
        Mono<String> deferMono = Mono.defer(() -> Mono.just(getCurrentTime()))
                .subscribeOn(Schedulers.parallel());
    }
*/
    //deferMono.subscribe(System.out::println);

    public static void main(String[] args) {
        Random random = new Random();
        // Crear un Mono que emite el valor actual del sistema cuando se suscribe
        Mono<String> deferMono = Mono.defer(() -> Mono.just(getCurrentTime()));
        // Suscribirse al Mono y procesar el valor emitido
        deferMono.subscribe(System.out::println);

        Mono<Integer> deferMono2 = Mono.defer(()->Mono.just(random.nextInt(30)));

        deferMono2.subscribe(element->{
            System.out.println("numero suscribe1: "+element+" operación->"+ Math.sqrt(element));
        });
        deferMono2.subscribe(
                element->{
                    System.out.println("numero suscribe2: "+element+" operación->"+ Math.sqrt(element));
                }
        );
    }


    // Método para obtener la hora actual
    private static String getCurrentTime() {
        return "Hora actual: " + System.currentTimeMillis();
    }

}
