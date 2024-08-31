package co.com.bancolombia.manipulacion.flujos.basicos;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;



public class ManipulacionFlujosBasicos {
   public static List<Persona> personas = Arrays.asList(
            new Persona("Juan", "Pérez", "123456789", 30, "Aries","123456789"),
            new Persona("María", "Gómez", "987654321", 25, "Virgo","4567890"),
            new Persona("Carlos", "Martínez", "555444333", 40, "Capricornio","111214124"),
            new Persona("Laura", "Rodríguez", "111222333", 35, "Tauro", "153545454545"),
            new Persona("Pedro", "Sánchez", "999888777", 28, "Leo", "132141244"),
            new Persona("Ana", "Fernández", "666777888", 22, "Acuario", "131234414414"),
            new Persona("David", "López", "333222111", 45, "Cáncer","8848848484"),
            new Persona("Sofía", "Díaz", "777666555", 32, "Géminis", "4444352525"),
            new Persona("Javier", "Hernández", "888999000", 27, "Escorpio","88686868699"),
            new Persona("Elena", "García", "112233445", 33, "Libra","9705842"),
            new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis","058586766"),
            new Persona("Rosa", "Jiménez", "998877665", 29, "Sagitario","2422424124"));

    //Crear un Flux a partir de la lista de personas.
    public static Flux<Persona> personaFlux = Flux.fromIterable(personas);


    public static void main(String[] args) {
        final String FIN = "Finish";
        //Filtrar las personas mayores de 30 años utilizando filter().
        personaFlux
                .filter(persona -> persona.getEdad() > 30)
                .subscribe(
                        element -> System.out.println("Persona mayor a 30 " + element),
                        error -> System.out.println("No hay personas mayores a 30"),
                        () -> System.out.println(FIN));

        //Mostrar los nombres de las personas mayores de 30 años utilizando map(), subscribe() y filter()
        personaFlux
                .filter(persona -> persona.getEdad() > 30)
                .map(persona -> persona.getNombre())
                .subscribe(
                        element -> System.out.println("Nombre persona mayor a 30 " + element),
                        error -> System.out.println("No hay personas mayores a 30"),
                        () -> System.out.println(FIN));

        //Crear un Mono con la primera persona de la lista.
        Mono<Persona> personaMono = Mono.just(personas.get(0));

        //Mostrar el nombre y apellido de la persona del Mono utilizando flatMap() y subscribe().
        personaMono.flatMap(persona -> {
            Mono<String> nombrePersona = Mono.just("Nombre completo " + persona.getNombre() + " " + persona.getApellido());
            return nombrePersona;
        }).subscribe(
                personaNombre -> System.out.println(personaNombre),
                error -> System.out.println("Error al buscar la persona"),
                () -> System.out.println(FIN)
        );

        //Agrupar a las personas por signo del zodiaco utilizando groupBy(), flatMap() y collectList(). Luego, mostrar el signo y la cantidad de personas para cada grupo. (Hacer uso de peek)
        personaFlux
                .groupBy(persona -> persona.getSigno())
                .flatMap(group -> group.collectList().map(groupSigno -> {
                        String signo = group.key();
                        int count = groupSigno.size();
                        return "Signo: " + signo + ", Cantidad de personas: " + count;
                    })).toStream()
                .peek(element -> System.out.println(element));

        //Crear una función obtenerPersonasPorEdad(int edad) que reciba una edad como parámetro y devuelva un Flux con las personas que tengan esa edad.
        obtenerPersonasPorEdad(25).subscribe(element -> System.out.println("Personas con edad 25: " + element));

        //Crear una función obtenerPersonasPorSigno(String signo) que reciba un signo del zodiaco como parámetro y devuelva un Flux con las personas que tengan ese signo. (Hacer uso de peek)
        obtenerPersonasPorSigno("Virgo").subscribe(element -> System.out.println("Personas con signo Virgo: " + element));

        //Crear una función obtenerPersonaPorTelefono(String telefono) que reciba un número de teléfono como parámetro y devuelva un Mono con la persona que tenga ese número de teléfono. Si no se encuentra, devolver un Mono vacío. (Hacer uso de peek)
        obtenerPersonaPorTelefono("2422424124").subscribe(element -> System.out.println("Persona por telefono " + element));
        //Crear una función obtenerPersonaPorTelefono(String telefono) que reciba un número de teléfono como parámetro y devuelva un Mono con la persona que tenga ese número de teléfono. Si no se encuentra, devolver un Mono vacío. (Hacer uso de peek)
        Persona person = new Persona("Prueba","Mono", "12345455",24,"Aries","34555552242");
        agregarPersona(person).subscribe(element -> System.out.println("Persona agregada " + element));

        //Crear una función eliminarPersona(Persona persona) que reciba una persona como parámetro y la elimine de la lista de personas. Devolver un Mono con la persona eliminada.
        eliminarPersona(person).subscribe(element -> System.out.println("Persona eliminada " + element));


    }

    //Crear una función obtenerPersonasPorEdad(int edad) que reciba una edad como parámetro y devuelva un Flux con las personas que tengan esa edad.
    public static Flux<Persona> obtenerPersonasPorEdad(int edad){
       Stream<Persona> personas =personaFlux.toStream()
               .peek(persona -> System.out.println("Valindando Stream "))
               .filter(persona -> persona.getEdad()==(edad));
       Flux<Persona> personaPorEdadFlux = Flux.fromStream(personas);
       return personaPorEdadFlux;
    }


    //Crear una función obtenerPersonasPorSigno(String signo) que reciba un signo del zodiaco como parámetro y devuelva un Flux con las personas que tengan ese signo. (Hacer uso de peek)
    public static Flux<Persona> obtenerPersonasPorSigno(String signo){
        Stream<Persona> personas =personaFlux.toStream()
                .peek(persona -> System.out.println("Valindando signo por personas "))
                .filter(persona ->persona.getSigno().equals(signo));
        Flux<Persona> personaPorSignoFlux = Flux.fromStream(personas);
        return personaPorSignoFlux;
    }

    //Crear una función obtenerPersonaPorTelefono(String telefono) que reciba un número de teléfono como parámetro y devuelva un Mono con la persona que tenga ese número de teléfono. Si no se encuentra, devolver un Mono vacío. (Hacer uso de peek)
    public static Mono<Persona> obtenerPersonaPorTelefono(String telefono){
        Stream<Persona> personas =personaFlux.toStream()
                .peek(persona -> System.out.println("Valindando telefonos por personas "))
                .filter(persona ->persona.getTelefono().equals(telefono));
        Optional<Persona> persona = personas.findFirst();
        return persona.map(Mono::just).orElse(Mono.empty());
    }
   // Crear una función agregarPersona(Persona persona) que reciba una persona como parámetro y la agregue a la lista de personas. Devolver un Mono con la persona agregada. (Hacer uso de peek)
    public static Mono<Persona> agregarPersona(Persona persona){
        Mono<Persona> personaAdd = Mono.just(persona);
        personaFlux.concatWith(personaAdd);
        return personaAdd;
    }

    //Crear una función eliminarPersona(Persona persona) que reciba una persona como parámetro y la elimine de la lista de personas. Devolver un Mono con la persona eliminada.
    public static Mono<Persona> eliminarPersona(Persona persona){
        Mono<Persona> personaRemove = Mono.just(persona);
        personaFlux = personaFlux.filter(personaTemp ->!personaTemp.equals(persona));
        return personaRemove;
    }
}