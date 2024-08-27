import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    static List<Persona> personas = new ArrayList<>();

    public static void main(String[] args) {

        // Crear la lista de personas
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

        // Crear un Flux a partir de la lista de personas
        Flux<Persona> personasFlux = Flux.fromIterable(personas);

        // Filtrar las personas mayores de 30 años
        personasFlux.filter(persona -> persona.getEdad() > 30)
                .map(Persona::getNombre)
                .subscribe(nombre -> System.out.println("Persona mayor de 30 años: " + nombre));

        // Crear un Mono con la primera persona de la lista
        Mono<Persona> primeraPersonaMono = Mono.justOrEmpty(personas.get(0));
        primeraPersonaMono.flatMap(persona -> {
            System.out.println("Primera persona: " + persona.getNombre() + " " + persona.getApellido());
            return Mono.just(persona);
        }).subscribe();

        // Agrupar a las personas por signo del zodiaco
        personasFlux.groupBy(Persona::getSigno)
                .flatMap(groupedFlux -> groupedFlux.collectList()
                        .flatMapMany(personasDelSigno -> {
                            System.out.println("Signo: " + groupedFlux.key() + " - Cantidad: " + personasDelSigno.size());
                            return Flux.fromIterable(personasDelSigno);
                        }))
                .subscribe();

        // Funciones para obtener personas por edad, signo y teléfono
        obtenerPersonasPorEdad(32).subscribe(persona -> System.out.println("Persona con 32 años: " + persona.getNombre()));
        obtenerPersonasPorSigno("Leo").subscribe(persona -> System.out.println("Persona con signo Leo: " + persona.getNombre()));
        obtenerPersonaPorTelefono("123456789").subscribe(persona -> System.out.println("Persona con teléfono 123456789: " + persona.getNombre()));

        // Agregar una nueva persona
        agregarPersona(new Persona("Miguel", "Suárez", "333444555", 40, "Aries"))
                .subscribe(persona -> System.out.println("Persona agregada: " + persona.getNombre() + " " + persona.getApellido() + " " +
                            persona.getTelefono() + " " + persona.getEdad()+ " " + persona.getSigno() ));

        // Eliminar una persona
        eliminarPersona(personas.get(0))
                .subscribe(persona -> System.out.println("Persona eliminada: " + persona.getNombre() + " " + persona.getApellido() + " " +
                        persona.getTelefono() + " " + persona.getEdad()+ " " + persona.getSigno() ));
    }

    // Función para obtener personas por edad
    public static Flux<Persona> obtenerPersonasPorEdad(int edad) {
        return Flux.fromIterable(personas)
                .filter(persona -> persona.getEdad() == edad)
                .doOnNext(persona -> System.out.println("Filtrando persona por edad: " + edad));
    }

    // Función para obtener personas por signo del zodiaco
    public static Flux<Persona> obtenerPersonasPorSigno(String signo) {
        return Flux.fromIterable(personas)
                .filter(persona -> persona.getSigno().equalsIgnoreCase(signo))
                .doOnNext(persona -> System.out.println("Filtrando persona por signo: " + signo));
    }

    // Función para obtener persona por teléfono
    public static Mono<Persona> obtenerPersonaPorTelefono(String telefono) {
        return Flux.fromIterable(personas)
                .filter(persona -> persona.getTelefono().equals(telefono))
                .next()
                .switchIfEmpty(Mono.empty())
                .doOnNext(persona -> System.out.println("Filtrando persona por teléfono: " + telefono));
    }

    // Función para agregar una nueva persona
    public static Mono<Persona> agregarPersona(Persona persona) {
        personas.add(persona);
        return Mono.just(persona)
                .doOnNext(p -> System.out.println("Agregando persona: " + persona.getNombre()));
    }

    // Función para eliminar una persona
    public static Mono<Persona> eliminarPersona(Persona persona) {
        personas.remove(persona);
        return Mono.just(persona)
                .doOnNext(p -> System.out.println("Eliminando persona: " + persona.getNombre()));
    }
}

class Persona {
    private String nombre;
    private String apellido;
    private String telefono;
    private int edad;
    private String signo;

    public Persona(String nombre, String apellido, String telefono, int edad, String signo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.edad = edad;
        this.signo = signo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getEdad() {
        return edad;
    }

    public String getSigno() {
        return signo;
    }
}
