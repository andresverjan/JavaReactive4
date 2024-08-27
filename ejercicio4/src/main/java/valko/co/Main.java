package valko.co;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import valko.co.model.Persona;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    private static final List<Persona> personas = new ArrayList<>();

    static {
        personas.add(new Persona("Juan", "Pérez", "123456789", 30, "Aries"));
        personas.add(new Persona("María", "Gómez", "987654321", 25, "Virgo"));
        personas.add(new Persona("Carlos", "Martínez", "555444333", 40, "Capricornio"));
        personas.add(new Persona("Laura", "Rodríguez", "111222333", 35, "Tauro"));
        personas.add(new Persona("Pedro", "Sánchez", "999888777", 28, "Leo"));
        personas.add(new Persona("Ana", "Fernández", "666777888", 22, "Acuario"));
        personas.add(new Persona("David", "López", "333222111", 45, "Cáncer"));
        personas.add(new Persona("Sofía", "Díaz", "777666555", 32, "Géminis"));
        personas.add(new Persona("Javier", "Hernández", "888999000", 27, "Escorpio"));
        personas.add(new Persona("Elena", "García", "112233445", 33, "Libra"));
        personas.add(new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis"));
        personas.add(new Persona("Rosa", "Jiménez", "998877665", 29, "Sagitario"));
    }

    public static void main(String[] args) {

        Flux<Persona> personasFlux = Flux.fromIterable(personas);

        personasFlux.filter(persona -> persona.getEdad() > 30)
                .map(Persona::getNombre)
                .subscribe(nombre -> System.out.println("Persona mayor de 30 años: " + nombre));

        Mono<Persona> personaMono = Mono.just(personas.get(0));

        personaMono.flatMap(persona -> Mono.just(persona.getNombre() + " " + persona.getApellido()))
                .subscribe(System.out::println);

        personasFlux.groupBy(Persona::getSigno)
                .flatMap(group -> group.collectList().map(list -> Map.entry(group.key(), list.size())))
                .doOnNext(entry -> System.out.println("Signo: " + entry.getKey() + ", Cantidad: " + entry.getValue()))
                .subscribe();

        obtenerPersonasPorEdad(30).subscribe(System.out::println);
        obtenerPersonasPorSigno("Virgo").subscribe(System.out::println);
        obtenerPersonaPorTelefono("123456789").subscribe(System.out::println);
        agregarPersona(new Persona("Luis", "Suárez", "111222333", 45, "Tauro")).subscribe(System.out::println);
        eliminarPersona(new Persona("Ana", "Fernández", "666777888", 22, "Acuario")).subscribe(System.out::println);
    }

    public static Flux<Persona> obtenerPersonasPorEdad(int edad) {
        return Flux.fromIterable(personas)
                .filter(persona -> persona.getEdad() == edad)
                .doOnNext(persona -> System.out.println("Encontrado por edad: " + persona));
    }

    public static Flux<Persona> obtenerPersonasPorSigno(String signo) {
        return Flux.fromIterable(personas)
                .filter(persona -> persona.getSigno().equalsIgnoreCase(signo))
                .doOnNext(persona -> System.out.println("Encontrado por signo: " + persona));
    }

    public static Mono<Persona> obtenerPersonaPorTelefono(String telefono) {
        return Flux.fromIterable(personas)
                .filter(persona -> persona.getTelefono().equals(telefono))
                .next()
                .doOnNext(persona -> System.out.println("Encontrado por teléfono: " + persona))
                .switchIfEmpty(Mono.empty());
    }

    public static Mono<Persona> agregarPersona(Persona persona) {
        personas.add(persona);
        return Mono.just(persona)
                .doOnNext(p -> System.out.println("Persona agregada: " + p));
    }

    public static Mono<Persona> eliminarPersona(Persona persona) {
        personas.remove(persona);
        return Mono.just(persona)
                .doOnNext(p -> System.out.println("Persona eliminada: " + p));
    }
}