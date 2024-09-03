package org.example;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final List<Persona> listaPersonas = new ArrayList<>();

    static {
        listaPersonas.add(new Persona("Juan", "Pérez", "123456789", 30, "Aries"));
        listaPersonas.add(new Persona("María", "Gómez", "987654321", 25, "Virgo"));
        listaPersonas.add(new Persona("Carlos", "Martínez", "555444333", 40, "Capricornio"));
        listaPersonas.add(new Persona("Laura", "Rodríguez", "111222333", 35, "Tauro"));
        listaPersonas.add(new Persona("Pedro", "Sánchez", "999888777", 28, "Leo"));
        listaPersonas.add(new Persona("Ana", "Fernández", "666777888", 22, "Acuario"));
        listaPersonas.add(new Persona("David", "López", "333222111", 45, "Cáncer"));
        listaPersonas.add(new Persona("Sofía", "Díaz", "777666555", 32, "Géminis"));
        listaPersonas.add(new Persona("Javier", "Hernández", "888999000", 27, "Escorpio"));
        listaPersonas.add(new Persona("Elena", "García", "112233445", 33, "Libra"));
        listaPersonas.add(new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis"));
        listaPersonas.add(new Persona("Rosa", "Jiménez", "998877665", 29, "Sagitario"));
    }


    //Crear un Flux a partir de la lista de personas.
    static Flux<Persona> fluxPersonas = Flux.fromIterable(listaPersonas);

    public static void main(String[] args) {

        /*
        System.out.println("===================================================================");
        System.out.println("Crear un Flux a partir de la lista de personas.");
        fluxPersonas.subscribe(System.out::println);

        System.out.println("===================================================================");
        System.out.println("Filtrar las personas mayores de 30 años utilizando filter().");
        Flux<Persona> fluxMayores30 = fluxPersonas.filter(persona -> persona.getEdad() > 30);
        fluxMayores30.subscribe(System.out::println);

        System.out.println("===================================================================");
        System.out.println("Mostrar los nombres de las personas mayores de 30 años utilizando map(), subscribe() y filter()");
        fluxPersonas.filter(persona -> persona.getEdad() > 30).map(Persona::getNombre).subscribe(System.out::println);

        System.out.println("===================================================================");
        System.out.println("Crear un Mono con la primera persona de la lista.");
        Mono<Persona> primeraPersona = Mono.just(listaPersonas.get(0));
        primeraPersona.subscribe(System.out::println);

        System.out.println("===================================================================");
        System.out.println("Mostrar el nombre y apellido de la persona del Mono utilizando flatMap() y subscribe().");
        //primeraPersona.map(persona -> List.of(persona.getNombre(), persona.getApellido())).subscribe(System.out::println);
        primeraPersona.flatMap(persona -> Mono.just(persona.getNombre() + " " + persona.getApellido()))
                .subscribe(System.out::println);

        System.out.println("===================================================================");
        System.out.println("Agrupar a las personas por signo del zodiaco utilizando groupBy(), flatMap() y collectList(). " +
                "Luego, mostrar el signo y la cantidad de personas para cada grupo.");
        Mono<List<Long>> fluxSignos = fluxPersonas.groupBy(Persona::getSigno).flatMap(Flux::count).collectList();

        System.out.println("===================================================================");
        System.out.println("Crear una función obtenerPersonasPorEdad(int edad) que reciba una edad como parámetro" +
                " y devuelva un Flux con las personas que tengan esa edad.");
        Disposable personasPorEdad = obtenerPersonasPorEdad(30);

        System.out.println("===================================================================");
        System.out.println("Crear una función obtenerPersonasPorSigno(String signo) que reciba un signo del zodiaco " +
                "como parámetro y devuelva un Flux con las personas que tengan ese signo.");
        Disposable personasPorSigno = obtenerPersonasPorSigno("Aries");

        System.out.println("===================================================================");
        System.out.println("Crear una función obtenerPersonaPorTelefono(String telefono) que reciba un número " +
                "de teléfono como parámetro y devuelva un Mono con la persona que tenga ese número de teléfono. " +
                "Si no se encuentra, devolver un Mono vacío.");
        Disposable personasPorTelefono = obtenerPersonasPorTelefono("554433221");

        System.out.println("===================================================================");
        System.out.println("Crear una función agregarPersona(Persona persona) que reciba una persona como parámetro " +
                "y la agregue a la lista de personas. Devolver un Mono con la persona agregada.");
        Persona nuevaPersona = new Persona("Paola", "Giraldo", "1112233344", 30, "Sagitario");
        Disposable personaAgregada = agregarPersona(nuevaPersona);


        System.out.println("===================================================================");
        System.out.println("Crear una función eliminarPersona(Persona persona) que reciba una persona como parámetro " +
                "y la elimine de la lista de personas. Devolver un Mono con la persona eliminada.");
        Persona eliminarPersona = new Persona("Paola", "Giraldo", "1112233344", 30, "Sagitario");
        Disposable personaEliminada = eliminarPersona(nuevaPersona);
*/

        Mono<Integer> source = Mono.just("error")
                .map(Integer::parseInt)
                .onErrorResume(error -> {
                    System.out.println("Error occurred: " + error.getMessage());
                    return Mono.just(0); // Proporcionar un valor alternativo en caso de error
                });

        source.subscribe(System.out::println);

        Flux<Integer> numbersFlux = Flux.just(1, 2, 3, 4, 5);
        Flux<Integer> transformedFlux = numbersFlux.map(number -> {
            if (number == 3) {
                throw new RuntimeException("Encountered an error processing element: " + number);
            }
            return number * 2;
        });
        transformedFlux.doOnError(error -> {
            System.err.println("An error occurred: " + error.getMessage());
        }).subscribe(
                System.out::println,
                // Handle errors emitted by the Flux
                error -> System.err.println("Error: " + error.getMessage())
        );



    }


/*    public static Disposable obtenerPersonasPorEdad(int edad) {
        return fluxPersonas
                .filter(persona -> persona.getEdad() == edad)
                .collect(Collectors.toList()).flatMapMany(Flux::fromIterable)
                .subscribe(System.out::println);
    }

    public static Disposable obtenerPersonasPorSigno(String signo) {
        return fluxPersonas
                .filter(persona -> persona.getSigno() == signo)
                .collect(Collectors.toList()).flatMapMany(Flux::fromIterable)
                .subscribe(System.out::println);
    }

    public static Disposable obtenerPersonasPorTelefono(String telefono) {
        return fluxPersonas
                .filter(persona -> persona.getTelefono() == telefono)
                .collect(Collectors.toList()).flatMapMany(Flux::fromIterable).switchIfEmpty(Mono.empty())
                .subscribe(System.out::println);
    }

    public static Disposable agregarPersona(Persona persona) {
        listaPersonas.add(persona);
        return Mono.just(persona).subscribe(System.out::println);
    }

    public static Disposable eliminarPersona(Persona persona) {
        listaPersonas.remove(persona);
        return Mono.just(persona).subscribe(System.out::println);
    }*/




}