package org.example;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Estudiante> estudiantes = List.of(
                new Estudiante("e1", List.of(
                        new Curso("curso1", List.of(
                                new Modulo("modulo1", "aaaajsdajdaljda"),
                                new Modulo("modulo2", "ewapjwaep")
                        )),
                        new Curso("curso2", List.of(
                                new Modulo("modulo1", "aaaajsdajdaljda"),
                                new Modulo("modulo2", "ewapjwaep"))),
                        new Curso("curso3", List.of(
                                new Modulo("modulo1", "aaaajsdajdaljda"),
                                new Modulo("modulo2", "ewapjwaep"),
                                new Modulo("m2", "ewapjwaep")))
                )),
                new Estudiante("e2", List.of(
                        new Curso("curso1", List.of(
                                new Modulo("modulo1", "aaaajsdajdaljda"),
                                new Modulo("modulo2", "ewapjwaep")
                        )),
                        new Curso("curso2", List.of(
                                new Modulo("modulo1", "aaaajsdajdaljda"),
                                new Modulo("modulo2", "ewapjwaep"))),
                        new Curso("curso3", List.of(
                                new Modulo("modulo1", "aaaajsdajdaljda"),
                                new Modulo("modulo2", "ewapjwaep")))
                )),
                new Estudiante("e3", List.of(
                        new Curso("curso1", List.of(
                                new Modulo("mod", "aaaajsdajdaljda"),
                                new Modulo("modulo2", "ewapjwaep"),
                                new Modulo("m2", "ewapjwaep"))),
                        new Curso("curso2", List.of(
                                new Modulo("modulo1", "aaaajsdajdaljda"),
                                new Modulo("modu2", "ewapjwaep"))),
                        new Curso("curso3", List.of(
                                new Modulo("modulo1", "aaaajsdajdaljda"),
                                new Modulo("m2", "ewapjwaep"),
                                new Modulo("m2", "ewapjwaep"),
                                new Modulo("m2", "ewapjwaep")))))
        );

        System.out.println("modulos del listado de estudiantes que tengan en su nombre una longitud mayor a 3 y retornar la propiedad name en Mayuscula.");
        estudiantes.stream()
                .flatMap(estudiante -> estudiante.getCourses().stream()
                        .flatMap(curso -> curso.getModules().stream()))
                                .filter(modulo -> modulo.getName().length() > 3)
                                .map(r -> r.getName().toUpperCase())
                                        .peek(System.out::println)
                                        .toList();

        System.out.println("cursos del listado de estudiantes que tengan mas de  2 modulos");
        estudiantes.stream()
                .flatMap(estudiante -> estudiante.getCourses().stream()
                .filter(curso -> curso.getModules().stream().toList().size() > 2)
                        .toList()
                        .stream().map(curso -> curso.getName()))
                .peek(System.out::println)
                .toList();

        System.out.println("listar todos los estudiantes");
        estudiantes.stream()
                .map(estudiante -> estudiante.getName())
                .peek(System.out::println)
                .toList();



    }
}