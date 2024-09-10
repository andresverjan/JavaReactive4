package com.curso_java.ejercicio_6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Crear módulos
        Module modulo1 = new Module("Calculo", "Modulo Calculo");
        Module modulo2 = new Module("Biologia", "Modulo Biologia");
        Module modulo3 = new Module("Historia", "Modulo Historia");
        Module modulo4 = new Module("Quimica", "Modulo Quimica");
        Module modulo5 = new Module("Trigonometria", "Modulo Trigonometria");
        Module modulo6 = new Module("Geometria", "Modulo Geometria");
        Module modulo7 = new Module("Geografia", "Modulo Geografia");

        // Crear cursos
        Course curso1 = new Course("Matematicas", List.of(modulo1, modulo5, modulo6));
        Course curso2 = new Course("Sociales", List.of(modulo3, modulo7));
        Course curso3 = new Course("Ciencias", List.of(modulo2, modulo4));

        // Crear estudiantes
        Student estudiante1 = new Student("Daniela", List.of(curso1, curso2));
        Student estudiante2 = new Student("Juan", List.of(curso2, curso3));
        Student estudiante3 = new Student("Maria", List.of(curso1, curso3));

        List<Student> estudiantes = new ArrayList<>(Arrays.asList(estudiante1, estudiante2, estudiante3));

        System.out.println("Módulos con nombre de longitud mayor a 3:");
        estudiantes.stream().
                flatMap(estudiante -> estudiante.getCourses().stream())
                .flatMap(curso -> curso.getModules().stream())
                .map(modulo -> modulo.getName())
                .filter(nombre -> nombre.length() > 3)
                .distinct()
                .toList()
                .forEach(modulo -> System.out.println(modulo));

        System.out.println("\nCursos con más de 2 módulos:");
        estudiantes.stream()
                .flatMap(estudainte -> estudainte.getCourses().stream())
                .filter(curso -> curso.getModules().size() > 2)
                .map(nombre -> nombre.getName())
                .distinct()
                .forEach(nombreString -> System.out.println(nombreString));

        System.out.println("\nListado de estudiantes:");
        estudiantes.stream()
                .map(nombre -> nombre.getName())
                .forEach(nombreString -> System.out.println(nombreString));
    }
}
