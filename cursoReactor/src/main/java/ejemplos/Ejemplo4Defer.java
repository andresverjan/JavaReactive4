package ejemplos;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Random;

public class Ejemplo4Defer {
    public static void main(String[] args) {

        //=========== FROM DEFER
        // Crear un Mono que emite el valor actual del sistema cuando se suscribe
        Mono<String> deferMono = Mono.defer(
                () -> Mono.just(getCurrentTime()))
                .subscribeOn(Schedulers.parallel());
        // Suscribirse al Mono y procesar el valor emitido

        System.out.println("Subscripcion de Defer: ");
        deferMono.subscribe( element -> System.out.println(element), error -> System.out.println("ERROR"));
        deferMono.subscribe(System.out::println);


        //========== FROM SUPPLIER
        Mono<String> monoSupplier = Mono.fromSupplier(
                () -> {
                    int randomValue = new Random().nextInt(10);
                    return String.valueOf(randomValue);
                }
        );
        System.out.println("Subscripcion de Supplier: ");
        monoSupplier.subscribe(element -> System.out.println(element));
        monoSupplier.subscribe(System.out::println);


        //=========== FROM CALLABLE
        Mono<String> monoCallable = Mono.fromCallable(() -> getRandomInt());

        System.out.println("Subscripcion de Callable: ");
        monoCallable.subscribe(element -> System.out.println(element));
        monoCallable.subscribe(element -> System.out.println(element));

    }


    // Método para obtener la hora actual
    private static String getCurrentTime() {
        return "Hora actual: " + System.currentTimeMillis();
    }

    private static String getRandomInt() {
        return "Aleatorio: " + new Random().nextInt(30);
    }
}
