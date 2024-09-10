package tarea5RepasoFlatMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsoFlatMap {
    public static void main(String[] args) {

        Course matematicas = new Course("Matematicas",
                List.of(new Module("Algebra", "Vectores Y funciones"),
                        new Module("Calculo", "Derivadas e integrales"),
                        new Module("Estadistica", "Probabilidades y distribuciones")));

        Course espanol = new Course("Espanol",
                List.of(new Module("Gramatica", "Sintaxis y morfologia")));

        Course ingles = new Course("Ingles",
                List.of(new Module("Gramatica", "Sintaxis y morfologia"),
                        new Module("Literatura", "Cuentos y novelas")));

        Course artistica = new Course("Artistica",
                List.of(new Module("Pintura", "Oleo"),
                        new Module("Escultura", "Arcilla"),
                        new Module("Dibujo", "Carboncillo")));

        Course ciencias = new Course("Ciencias",
                List.of(new Module("Biologia", "Celulas"),
                        new Module("Fisica", "Movimiento y energia"),
                        new Module("Quimica", "Elementos tabla periodica")));

        // 1. Agregar un listado de estudiantes y cada estudiante tiene minimo 3 courses,  1 cada course tiene 2 o 3
        // modulos.

        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("Juan", List.of(matematicas, espanol, ingles)));
        studentList.add(new Student("Maria", List.of(artistica, ciencias, matematicas)));
        studentList.add(new Student("Carlos", List.of(ingles, artistica, ciencias)));
        studentList.add(new Student("Laura", List.of(ciencias, matematicas, espanol)));

        // 2. Haciendo uso del FlatMap, listar todos los modulos del listado de estudiantes que tengan en su nombre una
        // longitud mayor a 3 y retornar la propiedad name en Mayuscula.

        List<String> listModules = studentList.stream()
                .flatMap(student -> student.getCourses().stream())
                .flatMap(course -> course.getModules().stream())
                .filter(module -> module.getName().length() > 3)
                .map(module -> module.getName().toUpperCase())
                .toList();

        listModules.forEach(System.out::println);

        // 3. Haciendo uso del FlatMap, listar todos los cursos del listado de estudiantes que tengan mas de  2 modulos.

        List<Course> listStudentsCurses = studentList.stream()
                .flatMap(student -> student.getCourses().stream())
                .filter(course -> course.getModules().size() > 2)
                .toList();

        listStudentsCurses.forEach(course -> System.out.println(course.getName()));

        // 4. Haciendo uso del Map, listar todos los estudiantes.

        List<String> listStudents = studentList.stream()
                .map(Student::getName)
                .toList();

        listStudents.forEach(System.out::println);
    }
}
