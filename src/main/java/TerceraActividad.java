import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class TerceraActividad {
    private static final List<Persona> personas = new ArrayList<>();

    static {
        personas.add(new Persona("Juan", "P\u00E9rez", "123456789", 30, "Aries"));
        personas.add(new Persona("Mar\u00EDa", "G\u00F3mez", "987654321", 25, "Virgo"));
        personas.add(new Persona("Carlos", "Mart\u00EDnez", "555444333", 40, "Capricornio"));
        personas.add(new Persona("Laura", "Rodr\u00EDguez", "111222333", 35, "Tauro"));
        personas.add(new Persona("Pedro", "S\u00E1nchez", "999888777", 28, "Leo"));
        personas.add(new Persona("Ana", "Fern\u00E1ndez", "666777888", 22, "Acuario"));
        personas.add(new Persona("David", "L\u00F3pez", "333222111", 45, "C\u00E1ncer"));
        personas.add(new Persona("Sof\u00EDa", "D\u00EDaz", "777666555", 32, "G\u00E9minis"));
        personas.add(new Persona("Javier", "Hern\u00E1ndez", "888999000", 27, "Escorpio"));
        personas.add(new Persona("Elena", "Garc\u00EDa", "112233445", 33, "Libra"));
        personas.add(new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis"));
        personas.add(new Persona("Rosa", "Jim\u00E9nez", "998877665", 29, "Sagitario"));
    }

    public static void main(String[] args) {
        Flux<Persona> personasFlux = Flux.fromIterable(personas);

        personasFlux.filter(persona -> persona.getEdad() > 30)
                .map(Persona::getNombre)
                .subscribe(nombre -> System.out.println("Persona mayor de 30 años: " + nombre + "\n"));

        Mono<Persona> primeraPersona = personasFlux.next();
        primeraPersona.flatMap(persona -> {
            System.out.println("Primera persona: " + persona.getNombre() + " " + persona.getApellido() + "\n");
            return Mono.just(persona);
        }).subscribe();

        personasFlux.groupBy(Persona::getSigno)
                .flatMap(group -> group.collectList().map(lista -> Map.entry(group.key(), lista.size())))
                .doOnNext(entry -> System.out.println("Signo: " + entry.getKey() + ", Cantidad: " + entry.getValue() + "\n"))
                .subscribe();

        obtenerPersonasPorEdad(32).subscribe(persona -> System.out.println("Persona con 32 años: " + persona.getNombre() + "\n"));

        obtenerPersonasPorSigno("Virgo").subscribe(persona -> System.out.println("Persona con signo Virgo: " + persona.getNombre() + "\n"));

        obtenerPersonaPorTelefono("123456789").subscribe(persona -> System.out.println("Persona con tel\u00E9fono 123456789: " + persona.getNombre() + "\n"));

        agregarPersona(new Persona("Nuevo", "Usuario", "123123123", 30, "Aries")).subscribe(persona -> System.out.println("Persona agregada: " + persona.getNombre() + "\n"));

        eliminarPersona(personas.get(0)).subscribe(persona -> System.out.println("Persona eliminada: " + persona.getNombre() + "\n"));
    }

    private static Flux<Persona> obtenerPersonasPorEdad(int edad) {
        return Flux.fromIterable(personas)
                .filter(persona -> persona.getEdad() == edad)
                .doOnNext(persona -> System.out.println("Filtrando persona por edad: " + persona + "\n"));
    }

    private static Flux<Persona> obtenerPersonasPorSigno(String signo) {
        return Flux.fromIterable(personas)
                .filter(persona -> persona.getSigno().equalsIgnoreCase(signo))
                .doOnNext(persona -> System.out.println("Filtrando persona por signo: " + persona + "\n"));
    }

    private static Mono<Persona> obtenerPersonaPorTelefono(String telefono) {
        return Flux.fromIterable(personas)
                .filter(persona -> persona.getTelefono().equals(telefono))
                .next()
                .doOnNext(persona -> System.out.println("Filtrando persona por tel\u00E9fono: " + persona + "\n"));
    }

    private static Mono<Persona> agregarPersona(Persona persona) {
        personas.add(persona);
        return Mono.just(persona).doOnNext(p -> System.out.println("Agregando persona: " + p + "\n"));
    }

    private static Mono<Persona> eliminarPersona(Persona persona) {
        personas.remove(persona);
        return Mono.just(persona).doOnNext(p -> System.out.println("Eliminando persona: " + p + "\n"));
    }
}
