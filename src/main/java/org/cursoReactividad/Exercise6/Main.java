package org.cursoReactividad.Exercise6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        //1. Agregar un listado de estudiantes y cada estudiante tiene minimo 3 courses,  1 cada course tiene 2 o 3 modulos.
        List<Student> studentList = Arrays.asList(new Student("Nicolas", Arrays.asList(new Course("Matematicas",
                Arrays.asList(new Module("Geometria", "descripcion"), new Module("Estadistica", "descripcion")
                        , new Module("Algebra", "descripcion"))))), new Student("Pepito",
                Arrays.asList(new Course("Ciencias Naturales", Arrays.asList(new Module("Biologia", "descripcion"), new Module("Quimica", "descripcion"),
                        new Module("Fisica", "descripcion"))))), new Student("Maria", Arrays.asList(new Course("Ingles", Arrays.asList(new Module("Reading", "descripcion"),
                new Module("Grammar", "descripcion"), new Module("Listening", "descripcion"))))));
        System.out.println("-----------------------------");
        System.out.println("EJERCICIO 2");
        System.out.println("-----------------------------");
        //2. Haciendo uso del FlatMap, listar todos los modulos del listado de estudiantes que tengan en su nombre una longitud mayor a 3 y retornar la propiedad name en Mayuscula.
        studentList.stream().flatMap(student -> student.getCourses().stream())
                .flatMap(course -> course.getModules().stream()).filter(module -> module.getName().length() > 3).
                map(module -> module.getName().toUpperCase()).toList().forEach(System.out::println);

        //3. Haciendo uso del FlatMap, listar todos los cursos del listado de estudiantes que tengan mas de  2 modulos.
        System.out.println("-----------------------------");
        System.out.println("EJERCICIO 3");
        System.out.println("-----------------------------");
        studentList.stream().flatMap(student -> student.getCourses().stream())
                .filter(course -> course.getModules().size() > 2)
                .map(Course::getName)
                .toList().forEach(System.out::println);

        // 4. Haciendo uso del Map, listar todos los estudiantes.
        System.out.println("-----------------------------");
        System.out.println("EJERCICIO 4");
        System.out.println("-----------------------------");
        studentList.stream().map(Student::getName).toList().forEach(System.out::println);




    }
}
