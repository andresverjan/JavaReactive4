package Ejercicio7;

import reactor.core.publisher.Mono;

public class ManejoErroresReactor {

    public static void main(String[] args) {
        procesarDatos("Dato válido");

        System.out.println("--------------------");

        procesarDatos("ERROR");
    }

    // Método que procesa datos con manejo de errores
    public static void procesarDatos(String dato) {
        Mono.just(dato)
                .map(value -> {
                    if ("ERROR".equals(value)) {
                        throw new RuntimeException("Error en el procesamiento");
                    }
                    return "Procesado: " + value;
                })
                .doOnError(error -> {
                    // Realiza alguna acción cuando se detecta un error
                    System.out.println("Ocurrió un error: " + error.getMessage());
                })
                .onErrorResume(error -> {
                    // Si ocurre un error, intenta procesar de nuevo con un valor alternativo
                    System.out.println("Intentando con valor alternativo...");
                    return Mono.just("Valor alternativo procesado");

                })
                .onErrorReturn("Valor por defecto") // Si el error no se resuelve, retorna un valor por defecto
                .subscribe(result -> System.out.println("Resultado final: " + result));
    }
}
