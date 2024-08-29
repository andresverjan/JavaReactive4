package Ejercicio4;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Defer {

    public static void main(String[] args) {
        // Crear un Mono que emite el valor actual del sistema cuando se suscribe
        Mono<String> deferMono = Mono.defer(() -> Mono.just(getCurrentTime()))
                .subscribeOn(Schedulers.parallel());
        // Suscribirse al Mono y procesar el valor emitido
        deferMono.subscribe(System.out::println);
    }


    // Método para obtener la hora actual
    private static String getCurrentTime() {
        return "Hora actual: " + System.currentTimeMillis();
    }


}
