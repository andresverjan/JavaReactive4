package valko.co;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Mono<Integer> mono = Mono.fromSupplier(() -> new Random().nextInt(50));

        mono.filter(number -> number > 10)
                .map(Math::sqrt)
                .subscribe(result -> System.out.println("Suscriptor 1 - Raíz cuadrada (número > 10): " + result));

        mono.filter(number -> number > 20)
                .map(Math::sqrt)
                .subscribe(result -> System.out.println("Suscriptor 2 - Raíz cuadrada (número > 20): " + result));

        List<String> paises = List.of("Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Paraguay");

        Flux<String> flux = Flux.fromIterable(paises);

        flux
                .doOnNext(p -> System.out.println("Lista de paise".concat(" ".concat(p))))
                .filter(pais -> pais.startsWith("C"))
                .subscribe(pais -> System.out.println("Suscriptor 3 - País que inicia con C: " + pais));

        flux.filter(pais -> pais.length() > 5)
                .map(Main::extraerVocales)
                .subscribe(vocales -> System.out.println("Suscriptor 4 - Vocales de países con longitud > 5: " + vocales));
    }

    private static String extraerVocales(String pais) {
        return pais.replaceAll("[^AEIOUaeiou]", "");
    }
}