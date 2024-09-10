package org.example;

import org.example.personas.Personas;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class Main {

    static List<Personas> personas = new ArrayList<>();
    public static void main(String[] args) {
        personas.add(new Personas("Juan", "Pérez", "1234567892", 30, "Aries", "123456789"));
        personas.add(new Personas("María", "Gómez", "9876543221", 25, "Virgo", "987654321"));
        personas.add(new Personas("Hola", "Gómez", "9876543212", 25, "Virgo", "987654321"));
        personas.add(new Personas("Carlos", "Martínez", "555444333", 40, "Capricornio", "555444333"));
        personas.add(new Personas("Laura", "Rodríguez", "111222333", 35, "Tauro", "111222333"));
        personas.add(new Personas("Pedro", "Sánchez", "999888777", 28, "Leo", "999888777"));
        personas.add(new Personas("Ana", "Fernández", "666777888", 22, "Acuario", "666777888"));
        personas.add(new Personas("David", "López", "333222111", 45, "Cáncer", "333222111"));
        personas.add(new Personas("Sofía", "Díaz", "777666555", 32, "Géminis", "777666555"));
        personas.add(new Personas("Javier", "Hernández", "888999000", 27, "Escorpio", "888999000"));
        personas.add(new Personas("Elena", "García", "112233445", 33, "Libra", "112233445"));
        personas.add(new Personas("Pablo", "Muñoz", "554433221", 38, "Piscis", "554433221"));
        personas.add(new Personas("Rosa", "Jiménez", "998877665", 29, "Sagitario", "998877665"));

        Flux<Personas> personasFlux = Flux.fromIterable(personas);
        Flux<Personas> personasMayoresDe30 = personasFlux.filter(persona -> persona.getEdad() > 30);
        System.out.println("Personas mayores de 30 años:");
        personasMayoresDe30.subscribe(persona -> System.out.println(persona.getNombre() + " " + persona.getApellido()));

        Mono<Personas> primeraPersonaMono = Mono.justOrEmpty(personas.get(0));
        System.out.println("Primera persona de la lista");
        primeraPersonaMono.subscribe(persona -> System.out.println(persona.getNombre() + " " + persona.getApellido()));


        System.out.println("Personas agrupadas por signo zodiacal:");
        personasFlux.groupBy(Personas::getSignoZodiacal)
                .flatMap(groupedFlux -> groupedFlux.collectList()
                        .map(list -> new Object[]{groupedFlux.key(), list.size()}))
                .subscribe(objects -> System.out.println("Signo: " + objects[0] + ", Cantidad: " + objects[1]));


        Flux<Personas> personasDeEdadEspecifica = obtenerPersonasPorEdad(30, personas);
        System.out.println("Personas de 30 años:");
        personasDeEdadEspecifica.subscribe(persona -> System.out.println(persona.getNombre() + " " + persona.getApellido()));

        Flux<Personas> personasPorSigno = obtenerPersonasPorSigno("Virgo", personas);
        System.out.println("Personas por signos:");
        personasPorSigno.subscribe(persona -> System.out.println(persona.getNombre() + " " + persona.getApellido()));

        Mono<Personas> personaPorTelefono = obtenerPersonaPorTelefono("123456789");
        System.out.println("Buscar Persona por teléfono:");
        personaPorTelefono.subscribe(persona -> System.out.println(persona.getNombre() + " " + persona.getApellido()));

        Personas nuevaPersona = new Personas("Juan Carlos", "Garcia", "1111144428", 20, "Aries", "31644113");
        Mono<Personas> personaAgregada = agregarPersona(nuevaPersona);
        System.out.println("Persona agregada:");
        personaAgregada.subscribe(persona -> System.out.println(persona.getNombre() + " " + persona.getApellido()));

        Mono<Personas> personaEliminada = eliminarPersona(nuevaPersona);
        System.out.println("Persona eliminada:");
        personaEliminada.subscribe(persona -> System.out.println(persona.getNombre() + " " + persona.getApellido()));

    }

    // Función obtenerPersonasPorEdad
    public static Flux<Personas> obtenerPersonasPorEdad(int edad, List<Personas> personas) {
        Flux<Personas> personasFlux = Flux.fromIterable(personas);
        return personasFlux.filter(persona -> persona.getEdad() == edad)
                .onErrorResume(error -> {
                    System.out.println("Error occurred: " + error.getMessage());
                    return Flux.empty();
                });
    }

    // Función obtenerPersonasPorSigno
    public static Flux<Personas> obtenerPersonasPorSigno(String signo, List<Personas> personas) {
        Flux<Personas> personasFlux = Flux.fromIterable(personas);
        return personasFlux.filter(persona -> persona.getSignoZodiacal().equals(signo))
                .onErrorResume(error -> {
                    System.out.println("Error occurred: " + error.getMessage());
                    return Flux.empty();
                });
    }

    // Función obtenerPersonaPorTelefono
    public static Mono<Personas> obtenerPersonaPorTelefono(String telefono) {
        Flux<Personas> personasFlux = Flux.fromIterable(personas);
        return personasFlux.filter(persona -> persona.getTelefono().equals(telefono)).next()
                .onErrorResume(error -> {
                    System.out.println("Error occurred: " + error.getMessage());
                    return Mono.empty();
                });
    }

    // Función agregarPersona
    public static Mono<Personas> agregarPersona(Personas persona) {
        personas.add(persona);
        return Mono.just(persona);
    }

    // Función eliminarPersona
    public static Mono<Personas> eliminarPersona(Personas persona) {
        personas.remove(persona);
        return Mono.just(persona);
    }

}