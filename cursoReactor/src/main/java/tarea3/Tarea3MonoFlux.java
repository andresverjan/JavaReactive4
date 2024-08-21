package tarea3;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;

public class Tarea3MonoFlux {

    public static double raizCuadrada(int n, int valor){
        if(n <= valor){
            throw new NullPointerException();
        }else {
            return Math.sqrt(n);
        }
    }
    public static void main(String[] args){
        final String FIN = "Finalizado";

        Mono<Integer> randomMono = Mono.just(new Random().nextInt(100));

        //SUBSCRIBER 1
        randomMono.subscribe(
                element -> System.out.printf("La raiz cuadrada de %d es %f%n", element, raizCuadrada(element, 10)),
                error -> System.out.println("[Error Mono] No se puede calcular la raiz."),
                () -> System.out.println(FIN)
        );

        //SUBSCRIBER 2
        randomMono.filter( element -> element > 10)
                .subscribe(
                element -> System.out.printf("La raiz cuadrada de %d es %f%n", element, Math.sqrt(element)),
                error -> System.out.println("[Error Mono] No se puede calcular la raiz."),
                () -> System.out.println(FIN)
        );



        Flux<String> paisesFlux = Flux.just("Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Paraguay", "Cuba", "Mexico", "EEUU");

        //SUBSCRIBER 1
        paisesFlux.filter( pais -> pais.startsWith("C"))
                .subscribe(
                    element -> System.out.printf("Pais que comienza con \"C\": %s%n" , element),
                    error -> System.out.println("[Error Flux] No se puede imprimir país."),
                    () -> System.out.println(FIN)
                );

        //SUBSCRIBER 2
        paisesFlux.filter(pais -> pais.length() > 5)
                .map(paisLetras -> paisLetras.replaceAll("[^aeiouAEIOU]",""))
                .subscribe(
                    element -> System.out.printf("Pais con mas de 5 letras y solo vocales: %s%n", element),
                    error -> System.out.println("[Error Flux] No se puede imprimir país."),
                    () -> System.out.println(FIN)
                );

    }
}
