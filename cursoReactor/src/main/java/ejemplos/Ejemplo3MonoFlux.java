package ejemplos;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static tarea2.Tarea2.factorial;
import static tarea2.Tarea2.sumatoriaN;

public class Ejemplo3MonoFlux {
    public static void main(String[] args){
        /*System.out.println("==========================================================");
        System.out.println("==============EJEMPLO DE MONO                 ============");
        System.out.println("==========================================================");

        Mono<String> helloMono = Mono.just("Hello");
        StepVerifier.create(helloMono)
                .expectNext("Hello")
                .expectComplete()
                .verify();

        Mono<String> mono = Mono.just("Hola, Mundo!");        // Suscribirse al Mono y procesar el elemento emitido
        mono.subscribe(
                element -> System.out.println("Elemento recibido: " + element),
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Secuencia completada")
        );

        Flux<String> stringFlux = Flux.just("Hello", "Sebastian");
        StepVerifier.create(stringFlux)
                .expectNext("Hello")
                .expectNext("Sebastian")
                .expectComplete()
                .verify();

        System.out.println("==========================================================");
        System.out.println("==============EJEMPLO DE FLUX                 ============");
        System.out.println("==========================================================");

        Flux<String> flux = Flux.just("Hola", "Mundo", "!");
        flux.subscribe(
                element -> System.out.println("Elemento recibido: " + element),
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Secuencia completada")
        );*/

        System.out.println("==========================================================");
        System.out.println("============== EJEMPLO CLASE  ============");
        System.out.println("==========================================================");

        Mono<String> monoNombre = Mono.just("Sebastian");
        monoNombre.subscribe(
                element -> System.out.printf("Se invierte el nombre %s: %s%n", element, new StringBuilder(element).reverse()),
                error -> System.out.println("No se pudo reversar el nombre"),
                () -> System.out.println("Finalizado")
        );
        monoNombre.subscribe(
                element -> System.out.printf("Se substrae el primer elemento del nombre %s: %s%n",element, element.substring(0,1)),
                error -> System.out.println("No se pudo sacar el primer elemento del nombre"),
                () -> System.out.println("Finalizado")
        );
        monoNombre.subscribe(
                element -> System.out.printf("Se substrae el elemento 20avo del nombre %s: %s%n", element, element.substring(19,20)),
                error -> System.out.println("No se pudo sacar el elemento veiteavo"),
                () -> System.out.println("Finalizado")
        );

        Flux<Integer> fluxNumeros = Flux.just(1, 2, 3, 4);

        fluxNumeros.subscribe(
                numero -> System.out.printf("El cuadrado del elemento %d es %d%n", numero, numero*numero),
                error -> System.out.println("No se pudo generar el cuadro del elemento."),
                () -> System.out.println("Finalizado")
        );

        fluxNumeros.subscribe(
                numero -> System.out.printf("El factorial del elemento %d es %d%n", numero, factorial(numero)),
                error -> System.out.println("No se pudo generar el "),
                () -> System.out.println("Finalizado")
        );

        fluxNumeros.subscribe(
                numero -> System.out.printf("La sumatoria del elemento %d es %d%n", numero, sumatoriaN(numero)),
                error -> System.out.println("No se pudo generar el "),
                () -> System.out.println("Finalizado")
        );

    }
}
