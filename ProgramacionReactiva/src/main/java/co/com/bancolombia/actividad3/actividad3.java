package co.com.bancolombia.actividad3;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;

public class actividad3 {
    public static double raizCuadrada(int n, int valor){
        if(n <= valor){
            throw new NullPointerException();
        }else {
            return Math.sqrt(n);
        }
    }
    public static void main(String[] args){
        final String FIN = "Finish";

        Mono<Integer> randomMono = Mono.just(new Random().nextInt(50)+1);


        randomMono.subscribe(
                element -> System.out.printf("La raiz cuadrada de  %d mayor a 10 es %f%n", element, raizCuadrada(element, 10)),
                error -> System.out.println("Error calculando la raiz cuadrada"),
                () -> System.out.println(FIN)
        );


        randomMono.filter( element -> element > 20)
                .subscribe(
                        element -> System.out.printf("La raiz cuadrada de %d mayor a 20 es %f%n", element, Math.sqrt(element)),
                        error -> System.out.println("Error calculando la raiz cuadrada"),
                        () -> System.out.println(FIN)
                );



        Flux<String> paisesFlux = Flux.just("Australia", "Bolivia", "Brasil", "Chile", "Colombia", "Paraguay", "Cuba", "Mexico", "Portugal");


        paisesFlux.filter( pais -> pais.startsWith("C"))
                .subscribe(
                        element -> System.out.printf("Pais que comienza con la letra C: %s%n" , element),
                        error -> System.out.println("No se encontraron paises que inicien con C."),
                        () -> System.out.println(FIN)
                );


        paisesFlux.filter(pais -> pais.length() > 5)
                .map(paisLetras -> paisLetras.replaceAll("[^aeiouAEIOU]",""))
                .subscribe(
                        element -> System.out.printf("Pais con mas de 5 letras y solo vocales: %s%n", element),
                        error -> System.out.println("No se encontraron vocales en los paises."),
                        () -> System.out.println(FIN)
                );

    }
}


