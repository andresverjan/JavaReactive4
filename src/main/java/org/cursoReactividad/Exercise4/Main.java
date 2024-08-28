package org.cursoReactividad.Exercise4;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    public static ArrayList<Person> peopleList;

    public static void main(String[] args) {
        peopleList = poblarLista();
        Flux<Person> peopleFlux = Flux.fromIterable(peopleList);
        peopleFlux
                .filter(person -> person.getAge() > 30)
                .map(Person::getName)
                .subscribe(name -> System.out.println("Name greater than: " + name));
        Mono<Person> addPerson = addPerson(new Person("Nicolas", "Quintero", "3218310156", 27, "Capricornio"));
        addPerson.subscribe(person -> System.out.println("new Person: " + person.getName()));

        System.out.println("-------------------Group by-----------------");
        peopleFlux.groupBy(Person::getStarSign)
                .flatMap(groupedFlux -> groupedFlux.collectList()
                        .flatMapMany(peopleforSigno -> {
                            System.out.println("Signo: " + groupedFlux.key() + " - Cantidad: " + peopleforSigno.size());
                            return Flux.fromIterable(peopleforSigno);
                        }))
                .subscribe();
        System.out.println("------------------------------------------------");

        Mono<Person> personMono = Mono.just(peopleList.stream().findFirst().get());
        personMono.subscribe(person -> System.out.println("List first: " + person.getName() + " " + person.getLastname()));

        Flux<Person> fluxAge = obtenerPersonasPorEdad(27);
        fluxAge.subscribe(person -> System.out.println("flux age: " + person.getName()));
        Flux<Person> fluxSign = obtenerPersonasPorSigno("Capricornio");
        fluxSign.subscribe(person -> System.out.println("Flux sign: " + person.getName()));
        Mono<Person> monoPhone = obtenerPersonaPorTelefono("666777888");
        monoPhone.subscribe(person -> System.out.println("Mono number: " + person.getName()));
        Mono<Person> removePerson = deletePerson(new Person("Nicolas", "Quintero", "3218310156", 27, "Capricornio"));
        removePerson.subscribe(person -> System.out.println("Delete person " + person.getName()));


    }

    public static ArrayList<Person> poblarLista() {
        ArrayList<Person> arrayList = new ArrayList();
        arrayList.add(new Person("Juan", "Pérez", "123456789", 30, "Aries"));
        arrayList.add(new Person("María", "Gómez", "987654321", 25, "Virgo"));
        arrayList.add(new Person("Carlos", "Martínez", "555444333", 40, "Capricornio"));
        arrayList.add(new Person("Laura", "Rodríguez", "111222333", 35, "Tauro"));
        arrayList.add(new Person("Pedro", "Sánchez", "999888777", 28, "Leo"));
        arrayList.add(new Person("Ana", "Fernández", "666777888", 22, "Acuario"));
        arrayList.add(new Person("David", "López", "333222111", 45, "Cáncer"));
        arrayList.add(new Person("Sofía", "Díaz", "777666555", 32, "Géminis"));
        arrayList.add(new Person("Javier", "Hernández", "888999000", 27, "Escorpio"));
        arrayList.add(new Person("Elena", "García", "112233445", 33, "Libra"));
        arrayList.add(new Person("Pablo", "Muñoz", "554433221", 38, "Piscis"));
        arrayList.add(new Person("Rosa", "Jiménez", "998877665", 29, "Sagitario"));
        return arrayList;
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

    public static Mono<Person> addPerson(Person person) {
        peopleList.add(person);
        return Mono.just(person);
    }

    public static Mono<Person> deletePerson(Person person) {
        peopleList.remove(person);
        return Mono.just(person);
    }

}
