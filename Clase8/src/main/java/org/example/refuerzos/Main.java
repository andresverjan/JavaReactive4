package org.example.refuerzos;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Module modulo1Curso1 = new Module("Algebra","modulo del curso Matematicas");
        Module modulo2Curso1 = new Module("Geometira","modulo del curso Matematicas");
        Module modulo1Curso2 = new Module("Fotosintesis","modulo del Biologia");
        Module modulo2Curso2 = new Module("ADN","modulo del curso Biologia");
        Module modulo3Curso2 = new Module("ARN","modulo del curso Biologia");
        Module modulo1Curso3 = new Module("Civilización egipcia","modulo del curso Sociales");
        Module modulo2Curso3 = new Module("Civilización romana","modulo del curso Sociales");

        Course curso1 = new Course("Matematicas",Arrays.asList(modulo1Curso1,modulo2Curso1));
        Course curso2 = new Course("Biologia",Arrays.asList(modulo1Curso2,modulo2Curso2,modulo3Curso2));
        Course curso3 = new Course("Sociales",Arrays.asList(modulo1Curso3,modulo2Curso3));

        Student estudiante1 = new Student("Ana", Arrays.asList(curso1,curso3));
        Student estudiante2 = new Student("Pedro", Arrays.asList(curso1,curso2,curso3));
        Student estudiante3 = new Student("Fernando", Arrays.asList(curso2,curso3));

        //1. Agregar un listado de estudiantes y cada estudiante tiene minimo 3 courses,  1 cada course tiene 2 o 3 modulos.
        List<Student> listStudents = Arrays.asList(estudiante1,estudiante2,estudiante3);

        //System.out.println(listaEstudiantes);
        //2. Haciendo uso del FlatMap, listar todos los modulos del listado de estudiantes que tengan en su nombre
        // una longitud mayor a 3 y retornar la propiedad name en Mayuscula.

        List<Module> listOfModulesForStudent = listStudents.stream()
                .flatMap(courseOfStudent->courseOfStudent.getCourses().stream())
                .flatMap(moduleOfCurse->moduleOfCurse.getModules().stream())
                .filter(module -> module.getName().length()>5)
                .collect(Collectors.toList());
        System.out.println("*--Punto 2--*");

        listOfModulesForStudent.forEach(module -> {
            System.out.println(module.getName().toUpperCase());
        });

       // 3. Haciendo uso del FlatMap, listar todos los cursos del listado de estudiantes que tengan mas de  2 modulos.
        List<Course> listCourseofStudents = listStudents.stream()
                .flatMap(courseOfStudent-> courseOfStudent.getCourses().stream())
                .filter(courses->courses.getModules().size()>2)
                .collect(Collectors.toList());

        System.out.println("*--Punto 3--*");
        listCourseofStudents.forEach(course -> {
            System.out.println(course.getName());
            System.out.println("*---\n"+course.getModules().stream().collect(Collectors.toList()));
        });

        //4. Haciendo uso del Map, listar todos los estudiantes.

        List<Student> listOfStudent = listStudents.stream()
                .map(student -> student)
                .collect(Collectors.toList());
        System.out.println("*--Punto 4--*");
        listOfStudent.forEach(student -> {
            System.out.println(student.toString());
        });


    }
}
