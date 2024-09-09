package co.com.bancolombia.manipulacion.flujos.basicos;


import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class flatMap {
    public static void main(String[] args) {
        List<Module> modulesInformatica = Arrays.asList(
                new Module("Modulo 1", "Introduccion al curso"),
                new Module("Modulo 2", "Informatica basica"));

        List<Module> modulesMatematicas = Arrays.asList(
                new Module("Modulo 1","Introduccion al curso"),
                new Module("Modulo 2", "Algebra basica"));

        List<Module> modulesSociales = Arrays.asList(
                new Module("Modulo 1","Introduccion al curso"),
                new Module("Modulo 2", "Comunicacion y respeto"),
                new Module("Modulo 3", "Ciencias Sociales")
                );

        List<Course> courseInformatica = Arrays.asList(
                new Course("Curso 1", modulesInformatica),
                new Course("Curso 2", modulesMatematicas)
        );

        List<Course> courseSociales = Arrays.asList(
                new Course("Curso 1", modulesSociales)
        );

        List<Course> courseMatematicas = Arrays.asList(
                new Course("Curso 1", modulesMatematicas),
                new Course("Curso 2", modulesSociales),
                new Course("Curso 3", modulesInformatica)
        );

        List<Student> studentList = Arrays.asList(
                new Student("Juan",courseMatematicas),
                new Student("Maria", courseInformatica),
                new Student("Carlos", courseSociales)
        );


        //Haciendo uso del FlatMap, listar todos los modulos del listado de estudiantes que tengan en su nombre una longitud mayor a 3 y retornar la propiedad name en Mayuscula.
        studentList.stream()
               .peek(student -> System.out.println("Antes de ingresar al flatMap " + student))
               .flatMap(student -> student.getCourses().stream())
               .peek(coursesPeek -> System.out.println("Primer flatMap course " + coursesPeek))
               .flatMap(course -> course.getModules().stream())
               .peek(modulePeek -> System.out.println("Segundo flatMap modules " + modulePeek))
               .filter(module -> module.getName().length() > 3)
               .peek(filterModule -> System.out.println("Luego del filter " + filterModule))
               .map(module -> module.getName().toUpperCase())
               .peek(mapModules -> System.out.println("Luego del map " +  mapModules))
               .forEach(System.out::println);

        //Haciendo uso del FlatMap, listar todos los cursos del listado de estudiantes que tengan mas de  2 modulos.
        studentList.stream()
                .flatMap(student -> student.getCourses().stream())
                .filter(course -> course.getModules().size() > 2)
                .forEach(System.out::println);

        //Haciendo uso del Map, listar todos los estudiantes.
        studentList.stream()
                .map(student -> student.toString())
                .forEach(System.out::println);
    }





}
