package foro1;

import reactor.core.publisher.Mono;

public class UsoOnError {
    public static void main(String[] args) {

    }
    /*public Mono<String> realizarRecarga(String userId, double monto) {
        return Mono.just(userId)
                // Intentar la recarga
                .flatMap(userService::debitarMonto)

                // Registrar error si el usuario no tiene suficiente dinero (doOnError)
                .doOnError(error -> {
                    if (error instanceof SaldoInsuficienteException) {
                        System.err.println("Error: El usuario no tiene suficiente dinero para la recarga.");
                    }
                })

                // Reintentar el débito con un servicio alternativo o mecanismo de reintento (onErrorResume)
                .onErrorResume(error -> {
                    if (error instanceof SaldoInsuficienteException) {
                        System.out.println("Intentando reintentar el débito con un servicio alternativo...");
                        return backupService.reintentarDebito(userId, monto);
                    }
                    return Mono.error(error); // Pasar otros errores no manejados
                })

                // Si falla el reintento, devolver un valor predeterminado (onErrorReturn)
                .onErrorReturn("Recarga fallida: no se pudo completar la operación.")

                // Suscribir el flujo
                .subscribe(resultado -> System.out.println("Resultado de la recarga: " + resultado));
    }*/
}
