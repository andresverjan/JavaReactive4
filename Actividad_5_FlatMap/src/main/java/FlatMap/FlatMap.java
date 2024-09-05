package FlatMap;

import java.util.Arrays;
import java.util.List;

public class FlatMap {
    public static void main(String[] args) {
        Module module1 = Module.builder().name("m 1").build();
        Module module2 = Module.builder().name("module 2").build();
        Module module3 = Module.builder().name("module 3").build();
        Module module4 = Module.builder().name("module 4").build();
        Module module5 = Module.builder().name("module 5").build();
        Module module6 = Module.builder().name("m 6").build();
        Module module7 = Module.builder().name("module 7").build();
        Module module8 = Module.builder().name("module 8").build();
        Module module9 = Module.builder().name("module 9").build();
        Module module10 = Module.builder().name("module 10").build();
        Module module11 = Module.builder().name("module 11").build();
        Module module12 = Module.builder().name("module 12").build();

        Course course1 = Course.builder().name("Course 1").modules(Arrays.asList(module1, module2)).build();        
        Course course2 = Course.builder().name("Course 2").modules(Arrays.asList(module3, module4)).build();
        Course course3 = Course.builder().name("Course 3").modules(Arrays.asList(module5, module6)).build();
        Course course4 = Course.builder().name("Course 4").modules(Arrays.asList(module7, module8, module1)).build();
        Course course5 = Course.builder().name("Course 5").modules(Arrays.asList(module9, module10)).build();
        Course course6 = Course.builder().name("Course 6").modules(Arrays.asList(module11, module12)).build();

        Student student1 = Student.builder().name("student1").courses(Arrays.asList(course1, course2, course3)).build();
        Student student2 = Student.builder().name("student2").courses(Arrays.asList(course4, course5, course6)).build();
        Student student3 = Student.builder().name("student3").courses(Arrays.asList(course2, course1, course6)).build();

        //1. Agregar un listado de estudiantes y cada estudiante tiene minimo 3 courses,  1 cada course tiene 2 o 3 modulos.
        List<Student> students = Arrays.asList(student1,student2,student3);

        //2. Haciendo uso del FlatMap, listar todos los modulos del listado de estudiantes que tengan en su nombre una longitud mayor a 3 y retornar la propiedad name en Mayuscula.
        students.stream()
                .flatMap(s -> s.getCourses().stream().flatMap(c -> c.getModules().stream()))
                .filter(m -> m.getName().length() > 3)
                .map(r -> r.getName().toUpperCase())
                .peek(System.out::println)
                .toList();

        //3. Haciendo uso del FlatMap, listar todos los cursos del listado de estudiantes que tengan mas de  2 modulos.
        students.stream()
                .flatMap(s -> s.getCourses().stream())
                .filter(m -> m.getModules().size() > 2)
                .map(Course::getName)
                .peek(System.out::println)
                .toList();

        //4. Haciendo uso del Map, listar todos los estudiantes.
        students.stream()
                .map(Student::getName)
                .peek(System.out::println)
                .toList();
    }
}
