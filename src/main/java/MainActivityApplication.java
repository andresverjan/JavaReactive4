import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class MainActivityApplication {

    public static void main(String[] args) {
        List<Student> students = createStudentList();
        // 1. Agregar un listado de estudiantes y cada estudiante tiene minimo 3 courses,  1 cada course tiene 2 o 3 modulos
        students.forEach(student -> {
            System.out.println("Estudiante: " + student.getName());
            student.getCourses().forEach(course -> {
                System.out.println("  Curso: " + course.getName());
                course.getModules().forEach(module -> {
                    System.out.println("    Módulo: " + module.getName() + " - Descripción: " + module.getDescripcion());
                });
            });
        });


        //2. Haciendo uso del FlatMap, listar todos los modulos del listado de estudiantes que tengan en su nombre una longitud mayor a 3 y retornar la propiedad name en Mayuscula.

        List<String> modulesUpper = students.stream()
                .flatMap(student -> student.getCourses().stream())
                .flatMap(course -> course.getModules().stream()).filter(module -> module.getName().length() > 3)
                .map(module -> module.getName().toUpperCase())
                .collect(Collectors.toList());
        System.out.println("\nModulos en mayusculas: \n");
        modulesUpper.forEach(System.out::println);

        //3. Haciendo uso del FlatMap, listar todos los cursos del listado de estudiantes que tengan mas de  2 modulos.

        students.stream()
                .flatMap(student -> student.getCourses().stream())
                .filter(course -> course.getModules().size() > 2)
                .collect(Collectors.toList());

        // 4. Haciendo uso del Map, listar todos los estudiantes.
        students.stream()
                .map(Student::getName)
                .collect(Collectors.toList());

    }

    private static List<Student> createStudentList() {
        Module module1 = new Module("Matem\u00E1ticas B\u00E1sicas", "Introducci\u00F3n a conceptos b\u00E1sicos de matem\u00E1ticas");
        Module module2 = new Module("\u00C1lgebra", "Estudio de ecuaciones y expresiones algebraicas");
        Module module3 = new Module("Geometr\u00EDa", "Estudio de figuras y propiedades geométricas");

        Module module4 = new Module("Historia Antigua", "Civilizaciones antiguas y su legado");
        Module module5 = new Module("Historia Moderna", "Eventos importantes de la historia reciente");

        Module module6 = new Module("Introducci\u00F3n a la F\u00EDsica", "Conceptos b\u00E1sicos de f\u00EDsica");
        Module module7 = new Module("F\u00EDsica Cu\u00E1ntica", "Teor\u00EDa y experimentos cu\u00E1nticos");

        Course course1 = new Course("Matem\u00E1ticas", Arrays.asList(module1, module2, module3));
        Course course2 = new Course("Historia", Arrays.asList(module4, module5));
        Course course3 = new Course("F\u00EDsica", Arrays.asList(module6, module7));

        Student student1 = new Student("Juan", Arrays.asList(course1, course2, course3));
        Student student2 = new Student("Ana", Arrays.asList(course1, course3));
        Student student3 = new Student("Luis", Arrays.asList(course2, course3, course1));

        return Arrays.asList(student1, student2, student3);

    }

}
