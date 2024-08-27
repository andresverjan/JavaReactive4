package org.cursoReactividad.Exercise4;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    private static final List<Person> peopleList = List.of(
            new Person("Juan", "Pérez", "123456789", 30, "Aries"),
            new Person("María", "Gómez", "987654321", 25, "Virgo"),
            new Person("Carlos", "Martínez", "555444333", 40, "Capricornio"),
            new Person("Laura", "Rodríguez", "111222333", 35, "Tauro"),
            new Person("Pedro", "Sánchez", "999888777", 28, "Leo"),
            new Person("Ana", "Fernández", "666777888", 22, "Acuario"),
            new Person("David", "López", "333222111", 45, "Cáncer"),
            new Person("Sofía", "Díaz", "777666555", 32, "Géminis"),
            new Person("Javier", "Hernández", "888999000", 27, "Escorpio"),
            new Person("Elena", "García", "112233445", 33, "Libra"),
            new Person("Pablo", "Muñoz", "554433221", 38, "Piscis"),
            new Person("Rosa", "Jiménez", "998877665", 29, "Sagitario"));

    public static void main(String[] args) {
        Flux<Person> peopleFlux = Flux.fromIterable(peopleList);
        peopleFlux
                .filter(person -> person.getAge() > 30)
                .map(Person::getName)
                .subscribe(name -> System.out.println("Name greater than: " + name));

        Mono<Person> personMono = Mono.just(peopleList.stream().findFirst().get());
        personMono.subscribe(person -> System.out.println("List first: " + person.getName() + " " + person.getLastname()));
        Flux<Person> fluxAge = obtenerPersonasPorEdad(30);
        fluxAge.subscribe(person -> System.out.println("flux age: " + person.getName()));
        Flux<Person> fluxSign = obtenerPersonasPorSigno("Virgo");
        fluxSign.subscribe(person -> System.out.println("Flux sign: " + person.getName()));
        Mono<Person> monoPhone = obtenerPersonaPorTelefono("666777888");
        monoPhone.subscribe(person -> System.out.println("Mono number: " + person.getName()));

    }


    public static Flux<Person> obtenerPersonasPorEdad(int age) {
        List<Person> people = peopleList.stream().filter(person -> person.getAge() == age).collect(Collectors.toList());
        return Flux.fromIterable(people);
    }

    public static Flux<Person> obtenerPersonasPorSigno(String signo) {
        List<Person> people = peopleList.stream().filter(person -> signo.equals(person.getStarSign())).collect(Collectors.toList());
        return Flux.fromIterable(people);
    }

    public static Mono<Person> obtenerPersonaPorTelefono(String telefono) {
        Optional<Person> person = peopleList.stream().filter(persona -> telefono.equals(persona.getPhone())).findFirst();
        return person.map(Mono::just).orElseGet(Mono::empty);
    }


}
