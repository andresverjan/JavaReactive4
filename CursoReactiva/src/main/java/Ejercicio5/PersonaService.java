package Ejercicio5;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PersonaService {

    private final List<Persona> personas;

    public PersonaService() {
        personas = new ArrayList<>();
        // Agregar las personas a la lista
        personas.add(new Persona("Juan", "Pérez", "123456789", 30, "Aries"));
        personas.add(new Persona("María", "Gómez", "987654321", 25, "Virgo"));
        personas.add(new Persona("Carlos", "Martínez", "555444333", 40, "Capricornio"));
        personas.add(new Persona("Laura", "Rodríguez", "111222333", 35, "Tauro"));
        personas.add(new Persona("Pedro", "Sánchez", "999888777", 28, "Leo"));
        personas.add(new Persona("Ana", "Fernández", "666777888", 22, "Acuario"));
        personas.add(new Persona("David", "López", "333222111", 45, "Cáncer"));
        personas.add(new Persona("Sofía", "Díaz", "777666555", 32, "Géminis"));
        personas.add(new Persona("Javier", "Hernández", "888999000", 27, "Escorpio"));
        personas.add(new Persona("Elena", "García", "112233445", 33, "Libra"));
        personas.add(new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis"));
        personas.add(new Persona("Rosa", "Jiménez", "998877665", 29, "Sagitario"));
    }

    //Punto 1
    public Flux<Persona> obtenerPersonas() {
        return Flux.fromIterable(personas);
    }

    //Punto 2 y 3
    public void filtrarPersonasMayoresDe30() {
        obtenerPersonas()
                .filter(persona -> persona.getEdad() > 30)
                .map(Persona::getNombre)
                .subscribe(System.out::println);
    }

    //Punto 4
    public Mono<Persona> obtenerPrimeraPersona() {
        return Mono.justOrEmpty(personas.stream().findFirst());
    }

    //Punto 5
    public void mostrarNombreYApellidoDePrimeraPersona() {
        obtenerPrimeraPersona()
                .flatMap(persona -> Mono.just(persona.getNombre() + " " + persona.getApellido()))
                .subscribe(System.out::println);
    }

    //Punto 6
    public void agruparPersonasPorSigno() {
        obtenerPersonas()
                .groupBy(Persona::getSigno)
                .flatMap(groupedFlux -> groupedFlux.collectList()
                        .map(personas -> Map.entry(groupedFlux.key(), personas.size())))
                .doOnNext(entry -> System.out.println("Signo: " + entry.getKey() + ", Cantidad: " + entry.getValue()))
                .subscribe();
    }

    //Punto 7
    public Flux<Persona> obtenerPersonasPorEdad(int edad) {
        return obtenerPersonas()
                .filter(persona -> persona.getEdad() == edad)
                .doOnNext(persona -> System.out.println("Persona encontrada: " + persona))
                .onErrorResume(error -> {
                    System.err.println("Error: " + error.getMessage());
                    return Flux.empty();
                });
    }

    //Punto 8
    public Flux<Persona> obtenerPersonasPorSigno(String signo) {
        return obtenerPersonas()
                .filter(persona -> persona.getSigno().equalsIgnoreCase(signo))
                .doOnNext(persona -> System.out.println("Persona encontrada: " + persona))
                .onErrorReturn(new Persona("Error", "Error", "Error", 0, "Error"));
    }

    //Punto 9
    public Mono<Persona> obtenerPersonaPorTelefono(String telefono) {
        return obtenerPersonas()
                .filter(persona -> persona.getTelefono().equals(telefono))
                .next()
                .doOnNext(persona -> System.out.println("Persona encontrada: " + persona))
                .onErrorReturn(new Persona("Error", "Error", "Error", 0, "Error"))
                .doOnError(error -> System.err.println("Error: " + error.getMessage()))
                .switchIfEmpty(Mono.empty());
    }

    //Punto 10
    public Mono<Persona> agregarPersona(Persona persona) {
        personas.add(persona);
        return Mono.just(persona)
                .doOnNext(p -> System.out.println("Persona agregada: " + p));
    }

    //Punto 11
    public Mono<Persona> eliminarPersona(Persona persona) {
        personas.remove(persona);
        return Mono.just(persona)
                .doOnNext(p -> System.out.println("Persona eliminada: " + p));
    }

}
