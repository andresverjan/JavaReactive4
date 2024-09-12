package org.example;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class EjercicioEntrega {
    private static List<Persona> personas = new ArrayList<>();
    private static Flux<Persona> flux = Flux.fromIterable(personas);
    public static void main(String[] args) {
        /*
        Persona persona1 = new Persona("Juan", "Pérez", "123456789", 30, "Aries");
        Persona persona2 = new Persona("María", "Gómez", "987654321", 25, "Virgo");
        Persona persona3 = new Persona("Carlos", "Martínez", "555444333", 40, "Capricornio");
        Persona persona4 = new Persona("Laura", "Rodríguez", "111222333", 35, "Tauro");
        Persona persona5 = new Persona("Pedro", "Sánchez", "999888777", 28, "Leo");
        Persona persona6 = new Persona("Ana", "Fernández", "666777888", 22, "Acuario");
        Persona persona7 = new Persona("David", "López", "333222111", 45, "Cáncer");
        Persona persona8 = new Persona("Sofía", "Díaz", "777666555", 32, "Géminis");
        Persona persona9 = new Persona("Javier", "Hernández", "888999000", 27, "Escorpio");
        Persona persona10 = new Persona("Elena", "García", "112233445", 35, "Libra");
        Persona persona11 = new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis");
        Persona persona12 = new Persona("Rosa", "Jiménez", "998877665", 29, "Sagitario");

         */
        Persona persona1 = new Persona("Juan", "Pérez", "123456789", 30, "Aries");
        Persona persona2 = new Persona("María", "Gómez", "987654321", 25, "Aries");
        Persona persona3 = new Persona("Carlos", "Martínez", "555444333", 40, "Aries");
        Persona persona4 = new Persona("Laura", "Rodríguez", "111222333", 35, "Aries");
        Persona persona5 = new Persona("Pedro", "Sánchez", "999888777", 28, "Aries");
        Persona persona6 = new Persona("Ana", "Fernández", "666777888", 22, "Aries");
        Persona persona7 = new Persona("David", "López", "333222111", 45, "Géminis");
        Persona persona8 = new Persona("Sofía", "Díaz", "777666555", 32, "Géminis");
        Persona persona9 = new Persona("Javier", "Hernández", "888999000", 27, "Géminis");
        Persona persona10 = new Persona("Elena", "García", "112233445", 35, "Libra");
        Persona persona11 = new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis");
        Persona persona12 = new Persona("Rosa", "Jiménez", "998877665", 29, "Sagitario");


        personas.add(persona1);
        personas.add(persona2);
        personas.add(persona3);
        personas.add(persona4);
        personas.add(persona5);
        personas.add(persona6);
        personas.add(persona7);
        personas.add(persona8);
        personas.add(persona9);
        personas.add(persona10);
        personas.add(persona11);
        personas.add(persona12);


        ///crear flux
        //Flux<Persona> flux = Flux.just(persona1,persona2,persona3,persona4,persona5,persona6,persona7,persona8,persona9,persona10,persona11,persona12);
        //Flux<Persona> flux = Flux.fromIterable(personas);

        //punto 2

        //flux.filter(persona -> persona.getEdad()>30).doOnNext(p-> System.out.println(p.getNombre())).subscribe();
        /*
        //punto3
        System.out.println("punto3");
        flux.filter(persona -> persona.getEdad()>30)
                .map(per -> per.getNombre() + " " +per.getEdad())
                .subscribe(System.out::println);
        //punto4

        Mono<Persona> monoPersona = flux.next();

        //punto 5

        monoPersona.subscribe(
                element->{
                    System.out.println("punto5 \n Nombre: "+element.getNombre()+" Apellido:"+element.getApellido());
                }
        );

        //punto6
        System.out.println("---*---punto 6---*---");

        flux.groupBy(Persona::getSigno)
                .flatMap(groupedFlux -> groupedFlux
                        .collectList() // Convertir cada grupo en una lista
                        .map(lista -> new Object[]{groupedFlux.key(), lista.size()}) // Crear un array con el signo y el tamaño de la lista
                )
                .subscribe(result -> System.out.println("Signo: " + result[0] + ", Cantidad de personas: " + result[1]));

        //punto 7
        System.out.println("---*---punto 7---*---");

        obtenerPersonasPorEdad(35)
                .subscribe(persona -> System.out.println("Persona por Edad:"+ persona.getNombre()));

        //punto 8
        System.out.println("---*---punto 8---*---");

        obtenerPersonasPorSigno("Capricornio")
                .subscribe(persona -> System.out.println("Persona por Signo:"+ persona.getNombre()));
        //punto 9

        System.out.println("---*---punto 9---*---");

        obtenerPersonasPorTelefono("0")
                .subscribe(persona -> System.out.println("Persona por telefono: "+persona.getNombre())
                );

        //punto 10
        Persona nuevaPersona = new Persona("Jose","Perez", "36985214",35,"Capricornio");

        agregarPersona(nuevaPersona).subscribe(persona -> System.out.println("---*punto 10---*\n"+persona.toString()));

        //punto 11
        eliminarPersona(persona5).subscribe(persona -> System.out.println("---*punto 11---*\n"+persona.toString()));

        System.out.println(personas);

        //////
        System.out.println("Complemento manejo de errores clase 8");
        */


        //errores
        System.out.println("---*---ERRORES---*---");

        flux.groupBy(Persona::getSigno)
                .flatMap(groupedFlux -> groupedFlux
                        .collectList() // Convertir cada grupo en una lista
                        .flatMap(lista->{
                            if (lista.size()>5){
                                return Mono.error(new RuntimeException("Limite de personas en grupo"));
                            }
                            return Mono.just(new Object[]{groupedFlux.key(), lista.size()});
                        })// Crear un array con el signo y el tamaño de la lista
                        .onErrorResume(error->{
                            System.err.println("error ocurred: "+ error.getMessage());
                            return Mono.just(new String[]{groupedFlux.key(),error.getMessage()});
                        })
                )
                .subscribe(result -> System.out.println("Signo: " + result[0] + ", Cantidad de personas: " + result[1]));



    }
    private static Flux<Persona> obtenerPersonasPorEdad(int edad){
      return flux.filter(persona -> persona.getEdad() == edad);
    }

    private static Flux<Persona> obtenerPersonasPorSigno(String signo){
        return flux.filter(persona -> persona.getSigno() == signo);
    }
    private static Mono<Persona> obtenerPersonasPorTelefono(String telefono){

        return flux.filter(persona -> persona.getTelefono().equals(telefono)).next();
    }

    private static Mono<Persona> agregarPersona(Persona nuevaPersona){
        return Mono.just(nuevaPersona).doOnNext(personas::add);
    }

    private  static Mono<Persona> eliminarPersona(Persona personaEliminar){
        return  flux.filter(persona->persona.equals(personaEliminar))
                .next()
                .flatMap(persona->{
                    if (personas.remove(personaEliminar)) {
                        return Mono.just(personaEliminar);
                    } else {
                        return Mono.empty();
                    }

                });
    }

}
