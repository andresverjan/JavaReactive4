package org.example.flujosbasico;

import org.example.flujosbasico.persona.Persona;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@SpringBootApplication
public class FlujosBasicoApplication {


    public static void main(String[] args) {

        //Crear una lista de personas con los siguientes datos:



        // 1. Crear un Flux a partir de la lista de personas
        Flux<Persona> personaFlux = Flux.fromIterable(personaList());


        // 2. Filtrar las personas mayores de 30 años utilizando filter().
        // 3. Mostrar los nombres de las personas mayores de 30 años utilizando map(), subscribe() y filter()
        System.out.println("\n----- 3. Mostrar los nombres de las personas mayores de 30 años utilizando map(), subscribe() y filter() -----");
        personaFlux
                .filter(persona -> persona.getEdad() > 30)
                .map(Persona::getNombre)
                .subscribe(nombre -> System.out.println(nombre + ", es mayor de 30 años"));

        // 4. Crear un Mono con la primera persona de la lista.

        System.out.println("\n----- 4. Crear un Mono con la primera persona de la lista. -----");

        personaFlux
            .next()
            .subscribe(persona -> System.out.println("Primera persona de la lista: " + persona.getNombre() + " " + persona.getApellido()));

        System.out.println("\n----- 6. Agrupar a las personas por signo del zodiaco utilizando groupBy(), flatMap() y collectList(). Luego, mostrar el signo y la cantidad de personas para cada grupo. -----");

        personaFlux
            .groupBy(Persona::getSigno)
            .flatMap(grupo -> grupo
                    .collectList()
                    .map(personasBySigno -> String.format(
                            "La cantidad de personas que pertenecen al signo %s son %s", grupo.key(), personasBySigno.size())))
            .subscribe(System.out::println);

        System.out.println("\n----- 7. Crear una función obtenerPersonasPorEdad(int edad) que reciba una edad como parámetro y devuelva un Flux con las personas que tengan esa edad. -----");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese su edad: ");
        String edad = scanner.next();
        Flux.fromIterable(obtenerPersonasPorEdad(Integer.valueOf(edad)))
                .subscribe(persona -> System.out.println(persona.getNombre() + " " +
                                persona.getApellido() + ", Edad: " + persona.getEdad()));

        System.out.println("\n-----Crear una función obtenerPersonasPorSigno(String signo) que reciba un signo del zodiaco como parámetro y devuelva un Flux con las personas que tengan ese signo. (Hacer uso de peek)---");

        System.out.print("Ingrese el signo: ");
        String signo = scanner.next();
        Flux.fromIterable(obtenerPersonasPorSigno(signo))
                .subscribe(persona -> System.out.println(persona.getNombre() + " " +
                        persona.getApellido() + ", Signo: " + persona.getSigno()));

        System.out.println("\n-----Crear una función agregarPersona(Persona persona) que reciba una persona como parámetro y la agregue a la lista de personas. Devolver un Mono con la persona agregada. (Hacer uso de peek)---");


        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.next();
        System.out.print("Ingrese el apellido: ");
        String apellido = scanner.next();
        System.out.print("Ingrese el telefono: ");
        String telefono = scanner.next();
        System.out.print("Ingrese el edad: ");
        String edadPersona = scanner.next();
        System.out.print("Ingrese el signo: ");
        String signoPersona = scanner.next();

        Persona persona = new Persona(nombre, apellido, telefono, Integer.valueOf(edadPersona), signoPersona);

        System.out.println("\n-----Crear una función agregarPersona(Persona persona) que reciba una persona como parámetro y la agregue a la lista de personas. Devolver un Mono con la persona agregada. (Hacer uso de peek)-----");

        agregarPersona(persona).subscribe();

        System.out.println("\n-----Crear una función eliminarPersona(Persona persona) que reciba una persona como parámetro y la elimine de la lista de personas. Devolver un Mono con la persona eliminada.-----");

        eliminarPersona(new Persona("Rosa", "Jiménez", "998877665", 29, "Sagitario")).subscribe();

        SpringApplication.run(FlujosBasicoApplication.class, args);
    }


    public static List<Persona> personaList() {
        List<Persona> personaList = new ArrayList<>();

        Persona persona1 = new Persona("Juan", "Pérez", "123456789", 30, "Aries");

        Persona persona2 = new Persona("María", "Gómez", "987654321", 25, "Virgo");

        Persona persona3 = new Persona("Carlos", "Martínez", "555444333", 40, "Capricornio");

        Persona persona4 = new Persona("Laura", "Rodríguez", "111222333", 35, "Tauro");

        Persona persona5 = new Persona("Pedro", "Sánchez", "999888777", 28, "Leo");

        Persona persona6 = new Persona("Ana", "Fernández", "666777888", 28, "Acuario");

        Persona persona7 = new Persona("David", "López", "333222111", 45, "Cáncer");

        Persona persona8 = new Persona("Sofía", "Díaz", "777666555", 32, "Géminis");

        Persona persona9 = new Persona("Javier", "Hernández", "888999000", 27, "Escorpio");

        Persona persona10 = new Persona("Elena", "García", "112233445", 33, "Libra");

        Persona persona11 = new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis");

        Persona persona12 = new Persona("Rosa", "Jiménez", "998877665", 29, "Sagitario");


        personaList.add(persona1);
        personaList.add(persona2);
        personaList.add(persona3);
        personaList.add(persona4);
        personaList.add(persona5);
        personaList.add(persona6);
        personaList.add(persona7);
        personaList.add(persona8);
        personaList.add(persona9);
        personaList.add(persona10);
        personaList.add(persona11);
        personaList.add(persona12);
        return personaList;
    }

    public static List<Persona> obtenerPersonasPorEdad(Integer edad) {
        return personaList()
            .stream()
            .filter(persona -> Objects.equals(persona.getEdad(), edad))
            .collect(Collectors.toList());
    }

    public static List<Persona> obtenerPersonasPorSigno(String signo) {
        return personaList()
                .stream()
                .filter(persona -> Objects.equals(persona.getSigno().toUpperCase(), signo.toUpperCase()))
                .collect(Collectors.toList());
    }

    public static Mono<Persona> agregarPersona(Persona persona) {
        List<Persona> personaList = personaList();
        return Mono.just(persona)
                .doOnNext(p -> System.out.println("Tamaño lista antes de agregar persona: " + personaList.size()))
                .doOnNext(personaList::add)
                .doOnNext(p -> System.out.println("La persona agregada a la lista es: " + p.getNombre() + " " + p.getApellido()))
                .doOnNext(p -> System.out.println("Tamaño lista despues de agregar persona: " + personaList.size()))
                .then(Mono.just(persona));
    }

    public static Mono<Persona> eliminarPersona(Persona persona) {
        List<Persona> personaList = personaList();
        return Mono.just(persona)
                .doOnNext(p -> System.out.println("Tamaño lista antes de eliminar persona: " + personaList.size()))
                .doOnNext(p -> personaList.remove(persona))
                .doOnNext(p -> System.out.println("La persona eliminada de la lista es: " + p.getNombre() + " " + p.getApellido()))
                .doOnNext(p -> System.out.println("Tamaño lista despues de eliminar persona: " + personaList.size()))
                .then(Mono.just(persona));
    }


}
