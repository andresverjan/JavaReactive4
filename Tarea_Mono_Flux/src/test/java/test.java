import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.*;

import java.util.Random;

public class test {
    @Test
    void testMonoWithDifferentSubscribers() {
        // Crear un Mono que emite un número aleatorio
        Mono<Integer> mono = Mono.fromSupplier(() -> new Random().nextInt(100)).cache();

        // Suscriptor 1: Imprimir el número
        mono.subscribe(value -> System.out.println("Suscriptor 1 - Número generado: " + value));

        // Suscriptor 2: Usar filter para comparar si el número es mayor a 10
        mono.filter(value -> value > 10)
                .doOnNext(value -> System.out.println("Número mayor a 10, raíz cuadrada: " + Math.sqrt(value)))
                .switchIfEmpty(Mono.fromRunnable(() -> System.out.println("No se emitió ningún valor mayor a 10")))
                .subscribe();


        // Suscriptor 3: Usar filter para comparar si el número es mayor a 10
        mono.filter(value -> value > 20)
                .doOnNext(value -> System.out.println("Número mayor a 20, raíz cuadrada: " + Math.sqrt(value)))
                .switchIfEmpty(Mono.fromRunnable(() -> System.out.println("No se emitió ningún valor mayor a 20")))
                .subscribe();
    }


    @Test
    void testFluxWithDifferentSubscribers() {
        // Crear un Flux que emite los nombres de los países
        Flux<String> paisesFlux = Flux.just("Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Paraguay");

        // Subscriber 1: Imprimir solo los nombres de países que comiencen con la letra C
        paisesFlux.filter(pais -> pais.startsWith("C"))
                .subscribe(pais -> System.out.println("Subscriber 1 - País con C: " + pais));

        // Subscriber 2: Identificar la longitud del nombre y, si es mayor a 5, imprimir las vocales
        paisesFlux.filter(pais -> pais.length() > 5)
                .map(pais -> "Vocales en " + pais + ": " + obtenerVocales(pais))
                .subscribe(vocales -> System.out.println("Subscriber 2 - " + vocales));
    }

    // Método auxiliar para obtener las vocales de un string
    private static String obtenerVocales(String pais) {
        return pais.replaceAll("[^AEIOUaeiou]", "");
    }

}
