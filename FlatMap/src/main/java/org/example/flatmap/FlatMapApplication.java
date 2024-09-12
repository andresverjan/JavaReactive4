package org.example.flatmap;

import org.example.flatmap.course.Course;
import org.example.flatmap.module.Module;
import org.example.flatmap.student.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class FlatMapApplication {

    public static void main(String[] args) {

        System.out.println("======================================================");

        List<List<Student>> listStudents = Arrays.asList(
            Arrays.asList(
                new Student("MATEO",
                    Arrays.asList(
                        new Course("Matematicas",
                            Arrays.asList(
                                    new Module("Geometria", "Clases de geometria"),
                                    new Module("Trigonometria", "Clases de trigonometria"),
                                    new Module("Derivadas", "Clases de derivadas")
                            )
                        ),
                        new Course("Sociales",
                                Arrays.asList(
                                        new Module("Historia", "Clases de historia"),
                                        new Module("Geografia", "Clases de Geografia")
                                )
                        ),
                        new Course("Programacion",
                                Arrays.asList(
                                        new Module("Imperactiva", "Clases de programación imperactiva"),
                                        new Module("Declarativa", "Clases de programación declarativa"),
                                        new Module("Reactiva", "Clases de programación reactiva")
                                )
                        )
                    )
                ),
                new Student("JUAN JOSE",
                    Arrays.asList(
                            new Course("Matematicas",
                                    Arrays.asList(
                                            new Module("Geometria", "Clases de geometria"),
                                            new Module("Derivadas", "Clases de derivadas")
                                    )
                            ),
                            new Course("Sociales",
                                    Arrays.asList(
                                            new Module("Historia", "Clases de historia"),
                                            new Module("Geografia", "Clases de Geografia"),
                                            new Module("Demografia", "Clases de Demografia")
                                    )
                            ),
                            new Course("Programacion",
                                    Arrays.asList(
                                            new Module("Imperactiva", "Clases de programación imperactiva"),
                                            new Module("Declarativa", "Clases de programación declarativa"),
                                            new Module("Reactiva", "Clases de programación reactiva")
                                    )
                            )
                    )
                ),
                new Student("SANTIAGO",
                    Arrays.asList(
                            new Course("Matematicas",
                                    Arrays.asList(
                                            new Module("Geometria", "Clases de geometria"),
                                            new Module("Trigonometria", "Clases de trigonometria"),
                                            new Module("Derivadas", "Clases de derivadas")
                                    )
                            ),
                            new Course("Sociales",
                                    Arrays.asList(
                                            new Module("Historia", "Clases de historia"),
                                            new Module("Geografia", "Clases de Geografia"),
                                            new Module("Demografia", "Clases de Demografia")
                                    )
                            ),
                            new Course("Programacion",
                                    Arrays.asList(
                                            new Module("Imperactiva", "Clases de programación imperactiva"),
                                            new Module("Declarativa", "Clases de programación declarativa")
                                    )
                            )
                    )
                )
            )
        );


        listStudents.stream()
            .flatMap(Collection::stream)
            .flatMap(s -> s.getCourses().stream()
                .flatMap(course -> course.getModules().stream()
                        .map(m -> m.getName().toUpperCase())
                        .filter(name -> name.length() > 3)
                )
            ).toList()
        .forEach(m -> System.out.println("El modulo " + m + " tiene un tamaño de " + m.length() + " caracteres"));

        System.out.println("\n=====================================================");

        listStudents.stream()
        .flatMap(Collection::stream)
        .flatMap(s -> s.getCourses().stream()
            .filter(c -> c.getModules().size() > 2)
            .map(Course::getName)
        ).toList()
        .forEach(m -> System.out.println("El curso de " + m + " tiene más de dos modulos"));

        System.out.println("\n=====================================================");

        listStudents.stream()
            .flatMap(Collection::stream)
            .map(Student::getName)
            .toList()
        .forEach(m -> System.out.println("El estudiante es " + m));


        SpringApplication.run(FlatMapApplication.class, args);
    }

}
