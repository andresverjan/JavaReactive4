package org.actividad_4;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        List<Persona> listPerson = Arrays.asList(new Persona("Juan", "Pérez", Arrays.asList(Arrays.asList("417312911","ext789"),Arrays.asList("123456789","ext911")), 30, "Aries"),
                new Persona("María", "Gómez", Arrays.asList(Arrays.asList("192380045","ext321"),Arrays.asList("987654321","ext045")), 25, "Virgo"),
                new Persona("Carlos", "Martínez", Arrays.asList(Arrays.asList("672111659","ext333"),Arrays.asList("555444333","ext659")), 40, "Capricornio"),
                new Persona("Laura", "Rodríguez", Arrays.asList(Arrays.asList("997860013","ext923"),Arrays.asList("111222333","ext013")), 35, "Tauro"),
                new Persona("Pedro", "Sánchez", Arrays.asList(Arrays.asList("380472977","ext830"),Arrays.asList("999888777","ext977")), 28, "Leo"),
                new Persona("Ana", "Fernández", Arrays.asList(Arrays.asList("037308466","ext742"),Arrays.asList("666777888","ext466")), 22, "Acuario"),
                new Persona("David", "López", Arrays.asList(Arrays.asList("403025251","ext751"),Arrays.asList("333222111","ext251")), 45, "Cáncer"),
                new Persona("Sofía", "Díaz", Arrays.asList(Arrays.asList("781072468","ext008"),Arrays.asList("777666555","ext468")), 32, "Géminis"),
                new Persona("Javier", "Hernández", Arrays.asList(Arrays.asList("154787393","ext072"),Arrays.asList("888999000","ext393")), 27, "Escorpio"),
                new Persona("Elena", "García", Arrays.asList(Arrays.asList("452522996","ext661"),Arrays.asList("112233445","ext996")), 33, "Libra"),
                new Persona("Pablo", "Muñoz", Arrays.asList(Arrays.asList("721416317","ext992"),Arrays.asList("554433221","ext317")), 38, "Piscis"),
                new Persona("Rosa", "Jiménez", Arrays.asList(Arrays.asList("151330456","ext652"),Arrays.asList("998877665","ext456")), 29, "Sagitario")
        );
        //Punto 1
        Flux<Persona> fluxPersonas = Flux.fromIterable(listPerson);
        //Punto 2
        fluxPersonas.filter(persona -> persona.getEdad()>30);
        //Punto 3
        System.out.println("Personas mayores de 30 años: ");
        fluxPersonas.filter(persona -> persona.getEdad()>30).map(Persona::getNombre).subscribe(nombre -> System.out.println(nombre));
        //Punto 4
        Mono<Persona> monoPersona = listPerson.stream().findFirst().map(Mono::just).orElse(Mono.empty());
        //Punto 5 Mostrar los numeros de telefono de la primera persona utilizando flatMap y subscribe
        System.out.println("Números de telefono de la primera persona en la lista: ");
        monoPersona.subscribe(persona -> System.out.println(persona.getTelefonos().stream().flatMap(t->t.stream()).collect(Collectors.toList())));
        //Punto 6
        fluxPersonas.groupBy(Persona::getSigno)
                .flatMap(groupedFlux -> groupedFlux
                        .count()
                        .map(count -> Map.entry(groupedFlux.key(), count))
                ).subscribe(e -> System.out.println(e.getKey() + ": " + e.getValue()));
        //Punto 7
        Flux<Persona> fluxEdad = obtenerPersonasPorEdad(29, listPerson);
        fluxEdad.subscribe(
                e -> System.out.println(e.getNombre())
        );
        //Punto 8
        Flux<Persona> fluxSigno = obtenerPersonasPorSigno("Sagitario", listPerson);
        fluxSigno.subscribe(
                e -> System.out.println(e.getNombre())
        );
        //Punto 9
        Mono<Persona> monoTelefono = obtenerPersonaPorTelefono(Arrays.asList("151330456","ext652"), listPerson);
        fluxSigno.subscribe(
                e -> System.out.println(e.getNombre())
        );
        //Punto 10
        Persona persona = new Persona("Camilo", "Naranjo", Arrays.asList(Arrays.asList("000000000","ext000"),Arrays.asList("11111111","ext111")), 24, "Sagitario");

        Mono<Persona> monoPersonaAdded = agregarPersona(persona, listPerson);
        monoPersonaAdded.subscribe(
                e -> System.out.println(e.getNombre())
        );
        //Punto 11

        Persona person = new Persona("Rosa", "Jiménez", Arrays.asList(Arrays.asList("151330456","ext652"),Arrays.asList("998877665","ext456")), 29, "Sagitario");

        Mono<Persona> monoPersonaRemoved = eliminarPersona(person, listPerson);
        monoPersonaAdded.subscribe(
                e -> System.out.println(e.getNombre())
        );

    }
    private static Flux<Persona> obtenerPersonasPorEdad(int edad, List<Persona> listPerson){
        return Flux.fromIterable(listPerson.stream().filter(e -> e.getEdad() == edad).peek(Flux::just).collect(Collectors.toList()));
    }
    private static Flux<Persona> obtenerPersonasPorSigno(String signo, List<Persona> listPerson) {
        return Flux.fromIterable(listPerson.stream().filter(e -> e.getSigno().equals(signo)).peek(Flux::just).collect(Collectors.toList()));
    }

    private static Mono<Persona> obtenerPersonaPorTelefono(List<String> telefono, List<Persona> listPerson) {
        return Mono.justOrEmpty(listPerson.stream()
                .filter(persona -> persona.getTelefonos().contains(telefono))
                .peek(Persona::getClass)
                .findFirst());
    }

    private static Mono<Persona> agregarPersona(Persona persona, List<Persona> listPerson) {
        listPerson.add(persona);
        return Mono.justOrEmpty(listPerson.stream().filter(e -> e.equals(persona)).peek(Persona::getClass).findFirst());

    }
    private static Mono<Persona> eliminarPersona(Persona persona, List<Persona> listPerson) {
        boolean removed = listPerson.remove(persona);
        if (removed) {
            return Mono.just(persona);
        } else {
            return Mono.empty();
        }
    }

}
