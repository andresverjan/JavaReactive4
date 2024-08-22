package flux;
import reactor.core.publisher.Flux;
import java.util.Arrays;
import java.util.List;

public class CountryFlux {
    public static void main(String[] args) {
        List<String> paises = Arrays.asList("Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Paraguay");

        Flux<String> fluxPaises = Flux.fromIterable(paises).publish();

        // Subscriber 1: Imprime países que inician con la letra C
        fluxPaises.filter(pais -> pais.startsWith("C"))
                .doOnNext(pais -> System.out.println("País que inicia con C: " + pais))
                .subscribe();

        // Subscriber 2: Imprime vocales de países con longitud mayor a 5
        fluxPaises.filter(pais -> pais.length() > 5)
                .map(pais -> {
                    StringBuilder vocales = new StringBuilder();
                    for (char c : pais.toCharArray()) {
                        if (esVocal(c)) {
                            vocales.append(c);
                        }
                    }
                    return vocales.toString();
                })
                .doOnNext(vocales -> System.out.println("Vocales del país: " + vocales))
                .subscribe();

        fluxPaises.connect();
    }

    private static boolean esVocal(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
                c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
    }


}
