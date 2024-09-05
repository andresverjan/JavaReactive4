package tarea4;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Flow;

public class Tarea4Operaciones {

    private static List<Persona> personasList;
    public static void print(String s){
        System.out.println("===================================================");
        System.out.println(s);
        System.out.println("===================================================");
    }
    public static void main(String[] args) {
        Persona persona1 = new Persona("Juan", "Pérez", "123456789", 30, "Aries");
        Persona persona2 = new Persona("Maria", "Gómez", "987654321", 25, "Virgo");
        Persona persona3 = new Persona("Carlos", "Martínez", "555444333", 40, "Virgo");
        Persona persona4 = new Persona("Laura", "Rodríguez", "111222333", 35, "Virgo");
        Persona persona5 = new Persona("Pedro", "Sánchez", "999888777", 28, "Aries");
        Persona persona6 = new Persona("Ana", "Fernández", "666777888", 22, "Acuario");
        Persona persona7 = new Persona("David", "López", "333222111", 45, "Cancer");
        Persona persona8 = new Persona("Sofia", "Díaz", "777666555", 32, "Geminis");
        Persona persona9 = new Persona("Javier", "Hernández", "888999000", 27, "Sagitario");
        Persona persona10 = new Persona("Elena", "García", "112233445", 33, "Libra");
        Persona persona11 = new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis");
        Persona persona12 = new Persona("Rosa", "Jiménez", "998877665", 29, "Sagitario");

       personasList = Arrays.asList(persona1, persona2, persona3, persona4, persona5, persona6, persona7, persona8,
                persona9, persona10, persona11, persona12);

        // Crear un Flux a partir de la lista de personas.
        Flux<Persona> personasFlux = Flux.fromIterable(personasList);


        // Filtrar las personas mayores de 30 años utilizando filter().
        // Mostrar los nombres de las personas mayores de 30 años utilizando map(), subscribe() y filter()
        print("Mostrar los nombres de las personas mayores de 30 años utilizando map(), subscribe() y filter()");
        personasFlux.filter(persona -> persona.getEdad() > 30)
                .map(persona -> persona.getNombre())
                .subscribe(
                        element -> System.out.printf("Persona mayor a 30 anios: %s%n", element),
                        error -> System.out.println("[Error Flux] No se puede imprimir persona."),
                        () -> System.out.println("Finalizado")
                );


        // Crear un Mono con la primera persona de la lista.
        Mono<Persona> personaMono = Mono.just(personasList.get(0));


        // Mostrar el nombre y apellido de la persona del Mono utilizando flatMap() y subscribe().
        print("Mostrar el nombre y apellido de la persona del Mono utilizando flatMap() y subscribe():");

        personaMono.flatMap(persona -> Mono.just(persona.getNombre() + " " + persona.getApellido())).subscribe(
                element -> System.out.printf("Nombre de la persona: %s%n", element),
                error -> System.out.println("[Error Flux] No se puede imprimir persona."),
                () -> System.out.println("Finalizado")
        );


        // Agrupar a las personas por signo del zodiaco utilizando groupBy(), flatMap() y collectList(). Luego, mostrar el signo y la cantidad de personas para cada grupo. (Hacer uso de peek)
        print("Agrupar a las personas por signo del zodiaco utilizando groupBy(), flatMap() y collectList(). Luego, mostrar el signo y la cantidad de personas para cada grupo. (Hacer uso de peek):");
        personasFlux.groupBy(persona -> persona.getSigno()).flatMap(group -> group.collectList().map(list -> {
            System.out.println("Signo: " + group.key() + ", Cantidad de personas: " + list.size());
            return list;
        })).subscribe();

        // Crear una función obtenerPersonasPorEdad(int edad) que reciba una edad como parámetro y devuelva un Flux con las personas que tengan esa edad. (Hacer uso de peek)
        print("Crear una función obtenerPersonasPorEdad(int edad) que reciba una edad como parámetro y devuelva un Flux con las personas que tengan esa edad. (Hacer uso de peek)");
        obtenerPersonasPorEdad(30).subscribe();

        // Crear una función obtenerPersonasPorSigno(String signo) que reciba un signo del zodiaco como parámetro y devuelva un Flux con las personas que tengan ese signo. (Hacer uso de peek)
        print("Crear una función obtenerPersonasPorSigno(String signo) que reciba un signo del zodiaco como parámetro y devuelva un Flux con las personas que tengan ese signo. (Hacer uso de peek)");
        obtenerPersonasPorSigno("Virgo").subscribe();

        // Crear una función obtenerPersonaPorTelefono(String telefono) que reciba un número de teléfono como parámetro y devuelva un Mono con la persona que tenga ese número de teléfono. Si no se encuentra, devolver un Mono vacío. (Hacer uso de peek)
        print("Crear una función obtenerPersonaPorTelefono(String telefono) que reciba un número de teléfono como parámetro y devuelva un Mono con la persona que tenga ese número de teléfono. Si no se encuentra, devolver un Mono vacío. (Hacer uso de peek)");
        obtenerPersonaPorTelefono("123456789").subscribe();

        // Crear una función agregarPersona(Persona persona) que reciba una persona como parámetro y la agregue a la lista de personas. Devolver un Mono con la persona agregada. (Hacer uso de peek)
        print("Crear una función agregarPersona(Persona persona) que reciba una persona como parámetro y la agregue a la lista de personas. Devolver un Mono con la persona agregada. (Hacer uso de peek)");
        Persona persona13 = new Persona("Sebastian", "Chavarria", "1212121212", 27, "Picis");
        agregarPersona(persona13).subscribe();

        // Crear una función eliminarPersona(Persona persona) que reciba una persona como parámetro y la elimine de la lista de personas. Devolver un Mono con la persona eliminada.
        print("Crear una función eliminarPersona(Persona persona) que reciba una persona como parámetro y la elimine de la lista de personas. Devolver un Mono con la persona eliminada.");
        eliminarPersona(persona13).subscribe();
    }

    private static Mono<Persona> eliminarPersona(Persona personaEliminar) {
        personasList.remove(personaEliminar);
        Mono<Persona> personaMono = Mono.just(personaEliminar);
        return personaMono.doOnNext(persona -> System.out.println("Persona eliminada: " + persona));
    }

    private static Mono<Persona> agregarPersona(Persona personaAgregar) {
        personasList.add(personaAgregar);
        Mono<Persona> personaMono = Mono.just(personaAgregar);
        return personaMono.doOnNext(persona1 -> System.out.println("Persona agregada: " + persona1));
    }

    private static Mono<Persona> obtenerPersonaPorTelefono(String number) {
        Mono<Persona> personaMono = Mono.just(
                personasList.stream().filter(persona -> persona.getTelefono().equals(number))
                .findFirst().orElse(null)
        );
        return personaMono.doOnNext(persona -> System.out.println("Persona con telefono " + number + ": " + persona));
    }

    public static Flux<Persona> obtenerPersonasPorEdad(int edad){
        Flux<Persona> personasFlux = Flux.fromIterable(personasList);
        return personasFlux.filter(persona -> persona.getEdad() == edad)
                .doOnNext(persona -> System.out.println("Persona con edad " + edad + ": " + persona));
    }
    private static Flux<Persona> obtenerPersonasPorSigno(String signo) {
        Flux<Persona> personasFlux = Flux.fromIterable(personasList);
        return personasFlux.filter(persona -> persona.getSigno().equals(signo))
                .doOnNext(persona -> System.out.println("Persona con signo " + signo + ": " + persona));
    }


}
