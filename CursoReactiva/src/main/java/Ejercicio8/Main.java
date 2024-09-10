package Ejercicio8;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        // Creación de módulos
        Module module1 = new Module("Java");
        Module module2 = new Module("Spring");
        Module module3 = new Module("React");
        Module module4 = new Module("SQL");
        Module module5 = new Module("Docker");
        Module module6 = new Module("Kubernetes");
        Module module7 = new Module("HTML");
        Module module8 = new Module("CSS");

        // Crear cursos
        Course course1 = new Course("Backend", Arrays.asList(module1, module2, module4));
        Course course2 = new Course("Frontend", Arrays.asList(module3, module7, module8));
        Course course3 = new Course("DevOps", Arrays.asList(module5, module6));
        Course course4 = new Course("Fullstack", Arrays.asList(module1, module2, module3));

        // Crear estudiantes
        Student student1 = new Student("Juan", Arrays.asList(course1, course2, course3));
        Student student2 = new Student("Maria", Arrays.asList(course1, course4));
        Student student3 = new Student("Carlos", Arrays.asList(course2, course3, course4));

        // Lista de estudiantes
        List<Student> students = Arrays.asList(student1, student2, student3);

        // 2. Haciendo uso del FlatMap, listar todos los modulos del listado de estudiantes que tengan en su nombre una longitud mayor a 3 y retornar la propiedad name en Mayuscula.
        System.out.println("Módulos con nombre mayor a 3 caracteres (en mayúscula) por estudiante:");
        students.forEach(student -> {
            List<String> modulosMayuscula = student.getCourses().stream()
                    .flatMap(course -> course.getModules().stream())
                    .map(Module::getName)
                    .filter(name -> name.length() > 3)
                    .map(String::toUpperCase)
                    .collect(Collectors.toList());
            System.out.println("Estudiante: " + student.getName());
            modulosMayuscula.forEach(System.out::println);
        });

        // 3. Haciendo uso del FlatMap, listar todos los cursos del listado de estudiantes que tengan mas de  2 modulos.
        System.out.println("\nCursos con más de 2 módulos por estudiante:");
        students.forEach(student -> {
            List<String> cursosConMasDeDosModulos = student.getCourses().stream()
                    .filter(course -> course.getModules().size() > 2)
                    .map(Course::getName)
                    .collect(Collectors.toList());
            System.out.println("Estudiante: " + student.getName());
            cursosConMasDeDosModulos.forEach(System.out::println);
        });

        // 4. Haciendo uso del Map, listar todos los estudiantes.
        System.out.println("\nNombres de los estudiantes:");
        students.stream()
                .map(Student::getName)
                .forEach(System.out::println);
    }
}

