package co.com.bancolombia.manipulacion.flujos.basicos;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;




//Agrupar a las personas por signo del zodiaco utilizando groupBy(), flatMap() y collectList(). Luego, mostrar el signo y la cantidad de personas para cada grupo.
//
//Crear una función obtenerPersonasPorEdad(int edad) que reciba una edad como parámetro y devuelva un Flux con las personas que tengan esa edad.
//
//Crear una función obtenerPersonasPorSigno(String signo) que reciba un signo del zodiaco como parámetro y devuelva un Flux con las personas que tengan ese signo.
//
//Crear una función obtenerPersonaPorTelefono(String telefono) que reciba un número de teléfono como parámetro y devuelva un Mono con la persona que tenga ese número de teléfono. Si no se encuentra, devolver un Mono vacío.
//
//Crear una función agregarPersona(Persona persona) que reciba una persona como parámetro y la agregue a la lista de personas. Devolver un Mono con la persona agregada.
public class ManipulacionFlujosBasicos {


    public static void main(String[] args) {
        final String FIN = "Finish";
        List<Persona> personas = Arrays.asList(
                new Persona("Juan", "Pérez", "123456789", 30, "Aries"),
                new Persona("María", "Gómez", "987654321", 25, "Virgo"),
                new Persona("Carlos", "Martínez", "555444333", 40, "Capricornio"),
                new Persona("Laura", "Rodríguez", "111222333", 35, "Tauro"),
                new Persona("Pedro", "Sánchez", "999888777", 28, "Leo"),
                new Persona("Ana", "Fernández", "666777888", 22, "Acuario"),
                new Persona("David", "López", "333222111", 45, "Cáncer"),
                new Persona("Sofía", "Díaz", "777666555", 32, "Géminis"),
                new Persona("Javier", "Hernández", "888999000", 27, "Escorpio"),
                new Persona("Elena", "García", "112233445", 33, "Libra"),
                new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis"),
                new Persona("Rosa", "Jiménez", "998877665", 29, "Sagitario"));

        //Crear un Flux a partir de la lista de personas.
        Flux<Persona> personaFlux = Flux.fromIterable(personas);

        //Filtrar las personas mayores de 30 años utilizando filter().
        personaFlux
                .filter(persona -> persona.getEdad() > 30)
                .subscribe(
                        element -> System.out.println("Persona mayor a 30 " + element),
                        error -> System.out.println("No hay personas mayores a 30"),
                        () -> System.out.println(FIN));


        //Mostrar los nombres de las personas mayores de 30 años utilizando map(), subscribe() y filter()
        personaFlux
                .filter(persona -> persona.getEdad()>30)
                .map(persona -> persona.getNombre())
                .subscribe(
                element -> System.out.println("Nombre persona mayor a 30 " + element),
                error -> System.out.println("No hay personas mayores a 30"),
                () -> System.out.println(FIN));

        //Crear un Mono con la primera persona de la lista.
        Mono<Persona> personaMono = Mono.just(personas.get(0));

        //Mostrar el nombre y apellido de la persona del Mono utilizando flatMap() y subscribe().
        personaMono.flatMap(persona -> {
            Mono<String> nombrePersona = Mono.just("Nombre persona Mono " + persona.getNombre());
            return nombrePersona;
        }).subscribe(
                personaNombre -> System.out.println(personaNombre),
                error -> System.out.println("Error al buscar la persona"),
                () -> System.out.println(FIN)
        );




    }

}
