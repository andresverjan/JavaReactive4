import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

public class Actividad4 {
    static Persona persona1 = new Persona("Juan", "Pérez", new String[]{"123456789",""}, 30, "Aries");
    static Persona persona2 = new Persona("María", "Gómez", new String[]{"987654321",""}, 25, "Virgo");
    static Persona persona3 = new Persona("Carlos", "Martínez", new String[]{"555444333"}, 40, "Capricornio");
    static Persona persona4 = new Persona("Laura", "Rodríguez", new String[]{"111222333"}, 35, "Tauro");
    static Persona persona5 = new Persona("Pedro", "Sánchez", new String[]{"999888777"}, 28, "Leo");
    static Persona persona6 = new Persona("Ana", "Fernández", new String[]{"666777888"}, 22, "Acuario");
    static Persona persona7 = new Persona("David", "López", new String[]{"333222111"}, 45, "Cáncer");
    static Persona persona8 = new Persona("Sofía", "Díaz", new String[]{"777666555"}, 32, "Géminis");
    static Persona persona9 = new Persona("Javier", "Hernández", new String[]{"888999000"}, 27, "Escorpio");
    static Persona persona10 = new Persona("Elena", "García", new String[]{"112233445"}, 33, "Libra");
    static Persona persona11 = new Persona("Pablo", "Muñoz", new String[]{"554433221"}, 38, "Piscis");
    static Persona persona12 = new Persona("Rosa", "Jiménez", new String[]{"998877665"}, 29, "Sagitario");
    //Punto 1
    static List<Persona> listaPersonas = Arrays.asList(persona1,persona2,persona3,persona4,persona5,persona6,persona7,persona8,persona9,persona10,persona11,persona12);

    public static void main(String[] args) {

        System.out.println("\nPunto 1:");
        Flux<Persona> fluxPersonas = Flux.fromIterable(listaPersonas);
        System.out.println("Flux<Persona> fluxPersonas = Flux.fromIterable(listaPersonas);");

        System.out.println("\nPunto 2:");
        fluxPersonas.filter(p -> p.getEdad() > 30);
        System.out.println("fluxPersonas.filter(p -> p.getEdad() > 30);");

        System.out.println("\nPunto 3:");
        fluxPersonas
                .filter(p -> p.getEdad() > 30)
                .map(Persona::getNombre)
                .subscribe(System.out::println);

        System.out.println("\nPunto 4:");
        Mono<Persona> monoPersona = Mono.justOrEmpty(listaPersonas.stream().findFirst());
        System.out.println("Mono<Persona> monoPersona = Mono.justOrEmpty(listaPersonas.stream().findFirst());");

        System.out.println("\nPunto 5:");
        monoPersona
                .subscribe(p -> {System.out.println(p.getNombre() + " " + p.getApellido());});

        System.out.println("\nPunto 6:");
        fluxPersonas
                .groupBy(Persona::getSigno)
                .flatMap(groupedFlux -> groupedFlux
                        .collectList()
                        .map(list -> Map.entry(groupedFlux.key(), list.size())))
                .collectList()
                .doOnNext(result -> result
                        .forEach(entry -> System.out.println("Signo: " + entry.getKey() + ", Cantidad: " + entry.getValue())))
                .block();

        System.out.println("\nPunto 7:");
        Flux<Persona> personasEdad = obtenerPersonasPorEdad(33);
        personasEdad
                .toStream()
                .peek(p -> {System.out.println("Persona de edad " + p.getEdad() + ": " + p.getNombre());})
                .toList();

        System.out.println("\nPunto 8:");
        Flux<Persona> personasSigno = obtenerPersonasPorSigno("Piscis");
        personasSigno
                .toStream()
                .peek(p -> {System.out.println("Persona de signo " + p.getSigno() + ": " + p.getNombre());})
                .toList();

        System.out.println("\nPunto 9:");
        Mono<Persona> personaTelefono = obtenerPersonaPorTelefono("998877665");
        personaTelefono
                .doOnNext(persona -> System.out.println("Persona encontrada: " + persona.getNombre()))
                .switchIfEmpty(Mono.fromRunnable(() -> System.out.println("Persona no encontrada, Mono está vacío")))
                .subscribe();

        System.out.println("\nPunto 10:");
        Persona nuevaPersona = new Persona("Angel","Vivas", new String[]{"311200000","3220000000"},30, "Piscis");
        Mono<Persona> nuevaPersonaMono = agregarPersona(nuevaPersona);

        System.out.println("\nPunto 11:");
        Persona personaAEliminar = new Persona("Juan", "Pérez", new String[]{"123456789",""}, 30, "Aries");
        Mono<Persona> nuevaEliminada = eliminarPersona(personaAEliminar);

    }

    public static Flux<Persona> obtenerPersonasPorEdad(int edad){
        return Flux.fromStream(listaPersonas.stream().filter(p -> p.getEdad() == edad));
    }

    public static Flux<Persona> obtenerPersonasPorSigno(String signo){
        return Flux.fromStream(listaPersonas.stream().filter(p -> p.getSigno().equals(signo)));
    }

    public static Mono<Persona> obtenerPersonaPorTelefono(String telefono) {
        Optional<Persona> persona = listaPersonas.stream()
                .filter(p -> Arrays.asList(p.getTelefonos()).contains(telefono))
                .peek(t -> System.out.println("Filtro de persona por telefono: " + telefono))
                .findFirst();

        return Mono.justOrEmpty(persona);
    }

    public static Mono<Persona> agregarPersona(Persona p){
        List<Persona> lista = new ArrayList<Persona>(listaPersonas);
        lista.add(p);

        lista.stream()
                .filter(t -> t.getNombre().equals(p.getNombre()))
                .peek(t -> System.out.println("Nueva persona agregada a la lista: " + p.getNombre()))
                .toList();

        return Mono.just(p);
    }

    public static Mono<Persona> eliminarPersona(Persona p){
        List<Persona> lista = new ArrayList<Persona>(listaPersonas);
        lista.stream()
                .filter(s -> s.getNombre().equals(p.getNombre()))
                .peek(t -> System.out.println("Persona a eliminar de la lista: " + p.getNombre()))
                .findFirst()
                .map(t -> {
                    lista.remove(t);
                    return t;
                });

        return Mono.just(p);
    }
}
