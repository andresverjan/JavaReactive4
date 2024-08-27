package org.example.flujosbasico;

import org.example.flujosbasico.persona.Persona;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

@SpringBootApplication
public class FlujosBasicoApplication {

    public static void main(String[] args) {

        //Crear una lista de personas con los siguientes datos:

        Persona persona1 = new Persona("Juan", "Pérez", "123456789", 30, "Aries");

        Persona persona2 = new Persona("María", "Gómez", "987654321", 25, "Virgo");

        Persona persona3 = new Persona("Carlos", "Martínez", "555444333", 40, "Capricornio");

        Persona persona4 = new Persona("Laura", "Rodríguez", "111222333", 35, "Tauro");

        Persona persona5 = new Persona("Pedro", "Sánchez", "999888777", 28, "Leo");

        Persona persona6 = new Persona("Ana", "Fernández", "666777888", 32, "Acuario");

        Persona persona7 = new Persona("David", "López", "333222111", 45, "Cáncer");

        Persona persona8 = new Persona("Sofía", "Díaz", "777666555", 32, "Géminis");

        Persona persona9 = new Persona("Javier", "Hernández", "888999000", 27, "Escorpio");

        Persona persona10 = new Persona("Elena", "García", "112233445", 33, "Libra");

        Persona persona11 = new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis");

        Persona persona12 = new Persona("Rosa", "Jiménez", "998877665", 29, "Sagitario");


        // 1. Crear un Flux a partir de la lista de personas
        Flux<Persona> personaFlux = Flux.just(persona1, persona2, persona3, persona4, persona5, persona6,
                persona7, persona8, persona9, persona10, persona11, persona12);


        // 2. Filtrar las personas mayores de 30 años utilizando filter().
        // 3. Mostrar los nombres de las personas mayores de 30 años utilizando map(), subscribe() y filter()
        System.out.println("----- 3. Mostrar los nombres de las personas mayores de 30 años utilizando map(), subscribe() y filter() -----");
        personaFlux
                .filter(persona -> persona.getEdad() > 30)
                .map(Persona::getNombre)
                .subscribe(nombre -> System.out.println(nombre + " es mayor de 30 años"));

        // 4. Crear un Mono con la primera persona de la lista.

        System.out.println("----- 4. Crear un Mono con la primera persona de la lista. -----");

        personaFlux
            .take(1)
            .next()
            .subscribe(persona -> System.out.println("El nombre de la primera persona de la lista es " + persona.getNombre() + " " + persona.getApellido()));

        System.out.println("----- 6. Agrupar a las personas por signo del zodiaco utilizando groupBy(), flatMap() y collectList(). Luego, mostrar el signo y la cantidad de personas para cada grupo. -----");

        personaFlux
            .groupBy(Persona::getSigno)
            .flatMap(grupo -> Mono.just(Map.of(grupo.key(), grupo.count())))
            .collectList()
            .subscribe(System.out::println);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String edad = scanner.next();

        System.out.println("----- 7. Crear una función obtenerPersonasPorEdad(int edad) que reciba una edad como parámetro y devuelva un Flux con las personas que tengan esa edad. -----");

        getPersonasPorEdad(Integer.valueOf(edad) , personaFlux)
                .map(Persona::getNombre)
                .subscribe(nombre -> System.out.println(nombre + " tiene " + edad + " años"));

        System.out.println("----- 7. Crear una función obtenerPersonasPorEdad(int edad) que reciba una edad como parámetro y devuelva un Flux con las personas que tengan esa edad. -----");

        SpringApplication.run(FlujosBasicoApplication.class, args);
    }


    public static Flux<Persona> getPersonasPorEdad(Integer edad, Flux<Persona> personaFlux) {
        return personaFlux
            .filter(persona -> Objects.equals(persona.getEdad(), edad));
    }

    public static Flux<Persona> getPersonasPorSigno(String signo, Flux<Persona> personaFlux) {
        return personaFlux
                .filter(persona -> Objects.equals(persona.getSigno(), signo));
    }

}
