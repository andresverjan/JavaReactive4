package Ejercicio;

import java.util.List;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        List <Student> Students = Arrays.asList(
                new Student ("Juan",Arrays.asList(
                        new Course("Math",Arrays.asList(new Module("Algebra",""),new Module("Geometry",""))),
                        new Course("Science",Arrays.asList(new Module("Fisica",""),new Module("Quimica",""))),
                        new Course("Science",Arrays.asList(new Module("Fisica",""),new Module("Quimica","")))

                )),
                new Student ("Ana",Arrays.asList(
                        new Course("Math",Arrays.asList(new Module("Algebra",""),new Module("Geometry",""))),
                        new Course("Science",Arrays.asList(new Module("Fisica",""),new Module("Quimica",""))),
                        new Course("Science",Arrays.asList(new Module("Fisica",""),new Module("Quimica","")))

                        ))
        );
        // 2. Listar todos los módulos con nombre de longitud mayor a 3 y en mayúsculas
        Students.stream()
                .flatMap(Student -> Student.getCourses().stream())
                .flatMap(Course -> Course.getModules().stream())
                .map(Module::getName)
                .filter(name -> name.length() > 3)
                .map(String::toUpperCase)
                .forEach(System.out::println);

        // 3. Listar todos los cursos que tengan más de 2 módulos
        Students.stream()
                .flatMap(Student -> Student.getCourses().stream())
                .filter(Course -> Course.getModules().size() > 2)
                .forEach(Course -> System.out.println(Course.getName()));

        // 4. Listar todos los estudiantes
        Students.stream()
                .map(Student::getName)
                .forEach(System.out::println);

    }
}
