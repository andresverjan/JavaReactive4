package org.example;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        Persona persona1 = new Persona("Juan", "Pérez", "123456789", 30, "Aries");
        Persona persona2 = new Persona("María", "Gómez", "987654321", 25, "Virgo");
        Persona persona3 = new Persona("Carlos", "Martínez", "555444333", 40, "Capricornio");
        Persona persona4 = new Persona("Laura", "Rodríguez", "111222333", 35, "Tauro");
        Persona persona5 = new Persona("Pedro", "Sánchez", "999888777", 28, "Leo");
        Persona persona6 = new Persona("Ana", "Fernández", "666777888", 22, "Acuario");
        Persona persona7 = new Persona("David", "López", "333222111", 45, "Cáncer");
        Persona persona8 = new Persona("Sofía", "Díaz", "777666555", 32, "Géminis");
        Persona persona9 = new Persona("Javier", "Hernández", "888999000", 27, "Escorpio");
        Persona persona10 = new Persona("Elena", "García", "112233445", 33, "Libra");
        Persona persona11 = new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis");
        Persona persona12 = new Persona("Rosa", "Jiménez", "998877665", 29, "Sagitario");

        //Crear un Flux a partir de la lista de personas.
        Flux<Persona> fluxPersonas = Flux.just(persona1,persona2,persona3,persona4,persona5,persona6,persona7,persona8,persona9,
                persona10,persona11,persona12);

        System.out.println("===================================================================");
        System.out.println("Filtrar las personas mayores de 30 años utilizando filter().");
        Flux<Persona> fluxMayores30 = fluxPersonas.filter(persona -> persona.getEdad() > 30);
        fluxMayores30.subscribe(System.out::println);

        System.out.println("===================================================================");
        System.out.println("Mostrar los nombres de las personas mayores de 30 años utilizando map(), subscribe() y filter()");
        fluxPersonas.filter(persona -> persona.getEdad() > 30).map(Persona::getNombre).subscribe(System.out::println);

        System.out.println("===================================================================");
        System.out.println("Crear un Mono con la primera persona de la lista.");
        Mono<Persona> primeraPersona = Mono.just(fluxPersonas.toStream().findFirst()).flatMap(Mono::justOrEmpty);

        System.out.println("===================================================================");
        System.out.println("Mostrar el nombre y apellido de la persona del Mono utilizando flatMap() y subscribe().");
        primeraPersona.map(persona -> Stream.of(persona.getNombre(), persona.getApellido())).().subscribe(System.out::println);

        System.out.println("===================================================================");
        System.out.println("Agrupar a las personas por signo del zodiaco utilizando groupBy(), flatMap() y collectList(). Luego, mostrar el signo y la cantidad de personas para cada grupo.");

        //Flux<Persona> fluxSignos = fluxPersonas.groupBy(Persona::getSigno).flatMap(s -> s.collect());



    }
}