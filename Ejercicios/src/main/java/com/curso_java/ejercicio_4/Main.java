package com.curso_java.ejercicio_4;

import io.reactivex.rxjava3.functions.Function;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Throwable {
        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona("Juan", "Pérez",  List.of("123456789", "987654321","101112131"), 30, "Aries"));
        personas.add(new Persona("María", "Gómez",  List.of("24681012", "000000000","111111111"), 25, "Virgo"));
        personas.add(new Persona("Carlos", "Martínez",  List.of("222222222", "333333333","444444444"), 40, "Capricornio"));
        personas.add(new Persona("Laura", "Rodríguez",  List.of("555555555", "66666666","777777777"), 35, "Tauro"));
        personas.add(new Persona("Pedro", "Sánchez",  List.of("888888888", "999999999","101010101"), 28, "Leo"));
        personas.add(new Persona("Ana", "Fernández",  List.of("121212121", "131313131","141414141"), 25, "Acuario"));
        personas.add(new Persona("David", "López",  List.of("151515151", "161616161","171717171"), 45, "Leo"));
        personas.add(new Persona("Sofía", "Díaz",  List.of("181818181", "191919191","212121212"), 32, "Géminis"));
        personas.add(new Persona("Javier", "Hernández",  List.of("232323232", "242424242","252525252"), 27, "Escorpio"));
        personas.add(new Persona("Elena", "García",  List.of("262626262", "272727272","282828282"), 33, "Libra"));
        personas.add(new Persona("Pablo", "Muñoz",  List.of("292929292", "313131313","323232323"), 38, "Leo"));
        personas.add(new Persona("Rosa", "Jiménez",  List.of("343434343", "353535353","363636363"), 25, "Sagitario"));

        Flux<Persona> personasFlux = Flux.fromIterable(personas);

        System.out.println("\n----------Nombres de las personas mayores de 30 años utilizando map, subscribe y filter----------");

        personasFlux
                .filter(persona -> persona.getEdad() > 30)
                .map(personaNombre -> personaNombre.getNombre())
                .subscribe(personaImpresa -> System.out.println(personaImpresa));

        System.out.println("\n-----Crear un Mono con la primera persona de la lista-----");

        Mono<Persona> primeraPersona = Mono.just(personasFlux.toStream().findFirst().get());
        primeraPersona.subscribe(persona -> System.out.println("Primer persona de la lista: " + persona.getNombre() + " " + persona.getApellido()));

        System.out.println("\n-----Mostrar el nombre, apellido y telefonos de la persona del Mono utilizando flatMap y subscribe-----");

        primeraPersona
                .flatMap(persona -> Mono.just("Nombre: " + persona.getNombre() + ", Apellido: " + persona.getApellido() + ", Teléfonos: " + persona.getTelefonos()))
                .subscribe(persona -> System.out.println(persona));

        System.out.println("\n-----Agrupar a las personas por signo del zodiaco utilizando groupBy, flatMap y collectList. Luego, mostrar el signo y la cantidad de personas para cada grupo-----");

        personasFlux
                .groupBy(persona -> persona.getSigno())
                .flatMap(signo -> signo.collectList()
                        .map(personasSigno -> "Signo: " + signo.key() + ", Cantidad de personas: " + personasSigno.size()))
                .subscribe(cantidadPersonas -> System.out.println(cantidadPersonas));

        System.out.println("\n-----Crear una función obtenerPersonasPorEdad que reciba una edad como parámetro y devuelva un Flux con las personas que tengan esa edad-----");

        Function<Integer, Flux<Persona>> obtenerPersonasPorEdad = crearObtenerPersonasPorEdad(personas);

        obtenerPersonasPorEdad.apply(25).subscribe(persona -> System.out.println(persona.getNombre()));

        System.out.println("\n-----Crear una función obtenerPersonasPorSigno que reciba un signo del zodiaco como parámetro y devuelva un Flux con las personas que tengan ese signo-----");

        Function<String, Flux<Persona>> obtenerPersonasPorSigno = crearObtenerPersonasPorSigno(personas);
        obtenerPersonasPorSigno.apply("Leo").subscribe(persona -> System.out.println(persona.getNombre()));

        System.out.println("\n-----Crear una función obtenerPersonaPorTelefono que reciba un número de teléfono como parámetro y devuelva un Mono con la persona que tenga ese número de teléfono. Si no se encuentra, devolver un Mono vacío-----");

        Function<String, Mono<List<Persona>>> obtenerPersonaPorTelefono = crearObtenerPersonasPorTelefono(personas);
        obtenerPersonaPorTelefono.apply("000000000").subscribe(
                personasEncontradas -> personasEncontradas.forEach(persona -> System.out.println(persona.getNombre()))
        );

        System.out.println("\n-----Crear una función agregarPersona que reciba una persona como parámetro y la agregue a la lista de personas. Devolver un Mono con la persona agregada-----");

        Function<Persona, Mono<Persona>> agregarPersona = crearAgregarPersona(personas);
        Persona nuevaPersona = new Persona("Daniela", "Gúzman", List.of("555444333"), 26, "Capricornio");
        agregarPersona.apply(nuevaPersona).subscribe(
                persona -> System.out.println(persona.getNombre() + " ha sido agregado.")
        );

        System.out.println("\n-----Crear una función eliminarPersona que reciba una persona como parámetro y la elimine de la lista de personas. Devolver un Mono con la persona eliminada-----");

        Function<Persona, Mono<Persona>> eliminarPersona = crearEliminarPersona(personas);
        Persona personaAEliminar = personas.get(0);
        eliminarPersona.apply(personaAEliminar).subscribe(
                persona -> System.out.println("Esta persona ha sido eliminada: " + persona.getNombre())
        );
    }

    private static Function<Integer, Flux<Persona>> crearObtenerPersonasPorEdad(List<Persona> personas) {
        return edad -> Flux.fromIterable(personas)
                .filter(persona -> persona.getEdad() == edad); // Filtra las personas por una edad en especifico
    }
    public static Function<String, Flux<Persona>> crearObtenerPersonasPorSigno(List<Persona> personas) {
        return signo -> Flux.fromIterable(personas)
                .filter(persona -> persona.getSigno().equalsIgnoreCase(signo));  // Filtra las personas por signo
    }
    public static Function<String, Mono<List<Persona>>> crearObtenerPersonasPorTelefono(List<Persona> personas) {
        return telefono -> Flux.fromIterable(personas)
                .filter(persona -> persona.getTelefonos().contains(telefono))  // Filtra las personas que tengan el teléfono especificado
                .collectList()  // Recoge los resultados en una lista
                .filter(lista -> !lista.isEmpty());  // Retorna Mono.empty() si la lista está vacía
    }
    public static Function<Persona, Mono<Persona>> crearAgregarPersona(List<Persona> personas) {
        return persona -> Mono.just(persona)  // Crea un Mono con la persona
                .doOnNext(p -> personas.add(p)); // Agrega la persona a la lista
    }
    public static Function<Persona, Mono<Persona>> crearEliminarPersona(List<Persona> personas) {
        return persona -> Mono.just(persona)  // Crea un Mono con la persona a eliminar
                .doOnNext(p -> personas.remove(p));  // Elimina la persona de la lista
    }
}
