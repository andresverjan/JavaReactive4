package org.example;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        1. Agregar un listado de estudiantes y cada estudiante tiene minimo 3 courses,  1 cada course tiene 2 o 3 modulos.
        List<Student> students = createStudents();

//        2. Haciendo uso del FlatMap, listar todos los modulos del listado de estudiantes
//        que tengan en su nombre una longitud mayor a 3 y retornar la propiedad name en Mayuscula.
        System.out.println("Listado de modulos con nombre mayor a 3 caracteres y en mayuscula");
        students.stream()
                .flatMap(student -> student.getCourses().stream())
                .flatMap(course -> course.getModules().stream())
                .filter(module -> module.getName().length() > 3)
                .map(module -> module.getName().toUpperCase())
                .peek(System.out::println)
                .toList();

//        3. Haciendo uso del FlatMap, listar todos los cursos del listado de estudiantes que tengan mas de  2 modulos.
        System.out.println("Listado de cursos con mas de 2 modulos");
        students.stream()
                .flatMap(student -> student.getCourses().stream())
                .filter(course -> course.getModules().size() > 2)
                .peek(course -> System.out.println(course.getName()))
                .toList();

//        4. Haciendo uso del Map, listar todos los estudiantes.
        System.out.println("Listado de estudiantes");
        students.stream()
                .map(Student::getName)
                .peek(System.out::println)
                .toList();

    }

    public static List<Student> createStudents() {
        return Arrays.asList(
                new Student("Juan", Arrays.asList(
                        new Course("Course 1", Arrays.asList(
                                new Module("Module 1", "Description 1"),
                                new Module("Module 2", "Description 2")
                        )),
                        new Course("Course 2", Arrays.asList(
                                new Module("Module 3", "Description 3"),
                                new Module("Module 4", "Description 4"),
                                new Module("Module 4 extra", "Description 4 extra")
                        )),
                        new Course("Course 3", Arrays.asList(
                                new Module("Module 5", "Description 5"),
                                new Module("Module 6", "Description 6")
                        ))
                )),
                new Student("Lia", Arrays.asList(
                        new Course("Course 4", Arrays.asList(
                                new Module("Module 7", "Description 7"),
                                new Module("Module 8", "Description 8")
                        )),
                        new Course("Course 5", Arrays.asList(
                                new Module("Module 9", "Description 9"),
                                new Module("Module 10", "Description 10")
                        )),
                        new Course("Course 6", Arrays.asList(
                                new Module("Module 11", "Description 11"),
                                new Module("Module 12", "Description 12"),
                                new Module("Module 12 extra", "Description 12 extra")
                        ))
                )),
                new Student("Dario", Arrays.asList(
                        new Course("Course 7", Arrays.asList(
                                new Module("Module 13", "Description 13"),
                                new Module("Module 14", "Description 14")
                        )),
                        new Course("Course 8", Arrays.asList(
                                new Module("Module 15", "Description 15"),
                                new Module("Module 16", "Description 16")
                        )),
                        new Course("Course 9", Arrays.asList(
                                new Module("Module 17", "Description 17"),
                                new Module("Module 18", "Description 18"),
                                new Module("Module 18 extra", "Description 18 extra")
                        ))
                ))
        );
    }
}