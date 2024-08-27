package com.example;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Main {
    private static List<Persona> personas = new ArrayList<>();

    static {
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

    public static void main(String[] args) {
        // Crear un Flux a partir de la lista de personas
        Flux<Persona> personasFlux = Flux.fromIterable(personas);

        // Filtrar las personas mayores de 30 años
        personasFlux.filter(persona -> persona.getEdad() > 30)
                .map(Persona::getNombre)
                .subscribe(System.out::println);

        // Crear un Mono con la primera persona de la lista
        Mono<Persona> primeraPersonaMono = Mono.just(personas.get(0));
        primeraPersonaMono.flatMap(persona -> Mono.just(persona.getNombre() + " " + persona.getApellido()))
                .subscribe(System.out::println);

        // Agrupar a las personas por signo del zodiaco
        personasFlux.groupBy(Persona::getSigno)
                .flatMap(groupedFlux -> groupedFlux.collectList()
                        .map(list -> Map.entry(groupedFlux.key(), list.size())))
                .subscribe(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));

        // Obtener personas por edad
        obtenerPersonasPorEdad(30).subscribe(System.out::println);

        // Obtener personas por signo
        obtenerPersonasPorSigno("Aries").subscribe(System.out::println);

        // Obtener persona por teléfono
        obtenerPersonaPorTelefono("123456789").subscribe(System.out::println);

        // Agregar una persona
        agregarPersona(new Persona("Nuevo", "Usuario", "000000000", 20, "Leo")).subscribe(System.out::println);

        // Eliminar una persona
        eliminarPersona(personas.get(0)).subscribe(System.out::println);
    }

    public static Flux<Persona> obtenerPersonasPorEdad(int edad) {
        return Flux.fromIterable(personas)
                .filter(persona -> persona.getEdad() == edad);
    }

    public static Flux<Persona> obtenerPersonasPorSigno(String signo) {
        return Flux.fromIterable(personas)
                .filter(persona -> persona.getSigno().equalsIgnoreCase(signo));
    }

    public static Mono<Persona> obtenerPersonaPorTelefono(String telefono) {
        return Flux.fromIterable(personas)
                .filter(persona -> persona.getTelefono().equals(telefono))
                .next();
    }

    public static Mono<Persona> agregarPersona(Persona persona) {
        personas.add(persona);
        return Mono.just(persona);
    }

    public static Mono<Persona> eliminarPersona(Persona persona) {
        personas.remove(persona);
        return Mono.just(persona);
    }
}