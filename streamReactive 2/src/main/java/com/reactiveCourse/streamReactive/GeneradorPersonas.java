package com.reactiveCourse.streamReactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class GeneradorPersonas implements GeneradorListasPersonas {
    private List<Persona> personas= new ArrayList<Persona>();
    @Override
    public Mono<Persona> agregarPersona(Persona persona) {
        personas.add(persona);
        return Mono.justOrEmpty(persona);
    }

    @Override
    public Flux<Persona> obtenerPersonas(String peek) {
        return Flux.fromStream(personas.stream().peek(
                v->System.out.println("Evaluando flux para: "+peek+" usuario "+v.getNombre()))
        );
    }

    @Override
    public void crearListaPersonas() {
        if(personas.isEmpty()){
            agregarPersona(Persona.builder()//persona 1
                    .nombre("Juan")
                    .apellido("Perez")
                    .telefono("123456789")
                    .edad(30)
                    .signo("Aries")
                    .build());
            agregarPersona(Persona.builder()//persona 2
                    .nombre("Maria")
                    .apellido("Gomez")
                    .telefono("987654321")
                    .edad(25)
                    .signo("Virgo")
                    .build());
            agregarPersona(Persona.builder()//persona 3
                    .nombre("Carlos")
                    .apellido("Martinez")
                    .telefono("555444333")
                    .edad(30)
                    .signo("Capricornio")
                    .build());
            agregarPersona(Persona.builder()//persona 4
                    .nombre("Laura")
                    .apellido("Rodriguez")
                    .telefono("111222333")
                    .edad(35)
                    .signo("Tauro")
                    .build());
            agregarPersona(Persona.builder()//persona 5
                    .nombre("Pedro")
                    .apellido("Sanchez")
                    .telefono("999888777")
                    .edad(28)
                    .signo("Leo")
                    .build());
            agregarPersona(Persona.builder()//persona 6
                    .nombre("Ana")
                    .apellido("Fernandez")
                    .telefono("666777888")
                    .edad(22)
                    .signo("Acuario")
                    .build());
            agregarPersona(Persona.builder()//persona 7
                    .nombre("David")
                    .apellido("Lopez")
                    .telefono("333222111")
                    .edad(45)
                    .signo("Cancer")
                    .build());
            agregarPersona(Persona.builder()//persona 8
                    .nombre("Sofia")
                    .apellido("Diaz")
                    .telefono("777666555")
                    .edad(32)
                    .signo("Geminis")
                    .build());
            agregarPersona(Persona.builder()//persona 9
                    .nombre("Javier")
                    .apellido("Hernandez")
                    .telefono("888999000")
                    .edad(27)
                    .signo("Escorpio")
                    .build());
            agregarPersona(Persona.builder()//persona 10
                    .nombre("Elena")
                    .apellido("Garcia")
                    .telefono("112233445")
                    .edad(33)
                    .signo("Libra")
                    .build());
            agregarPersona(Persona.builder()//persona 11
                    .nombre("Pablo")
                    .apellido("Muñoz")
                    .telefono("554433221")
                    .edad(38)
                    .signo("Piscis")
                    .build());
            agregarPersona(Persona.builder()//persona 12
                    .nombre("Rosa")
                    .apellido("Jimenez")
                    .telefono("998877665")
                    .edad(29)
                    .signo("Sagitario")
                    .build());
        }

    }

    @Override
    public Flux<Persona> obtenerPersonasPorEdad(int edad) {
        return Flux.fromStream(personas.stream().peek(
                v->System.out.println("Evaluando "+v.getNombre()+" "+v.getApellido()+" por edad"))
                .filter(p->p.getEdad()==edad).peek(
                        p->System.out.println("Persona: "+p.getNombre()+" "+p.getApellido()+" edad: "+p.getEdad())));
    }

    @Override
    public Flux<Persona> obtenerPersonasPorSigno(String signo) {
        return Flux.fromStream(personas.stream().peek(
                        v->System.out.println("Evaluando "+v.getNombre()+" "+v.getApellido()+" por signo"))
                .filter(p->p.getSigno()==signo).peek(
                        p->System.out.println("Persona: "+p.getNombre()+" "+p.getApellido()+" signo: "+p.getSigno())));
    }

    @Override
    public Mono<Persona> obtenerPersonasPorTelefono(String telefono) {
        return Mono.justOrEmpty(
                personas.stream()
                        .filter(p->p.getTelefono()==telefono)
                        .peek(p->System.out.println("Encontrado con telefono "+p))
                        .findFirst());
    }

    @Override
    public Mono<Persona> eliminarPersona(Persona persona) {
        if(personas.remove(persona)) return  Mono.just(persona);
        else return Mono.empty();
    }
}
