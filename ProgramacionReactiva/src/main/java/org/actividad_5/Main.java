package org.actividad_5;

import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Module module1 = new Module("Module 1","This is the module 1");
        Module module2 = new Module("Module 2","This is the module 2");
        Module module3 = new Module("Module 3","This is the module 3");

        Course course1 = new Course("English", List.of(module1,module2));
        Course course2 = new Course("Math", List.of(module1,module2,module3));
        Course course3 = new Course("Science", List.of(module1,module2));

        Student student1 = new Student("Martin", List.of(course1,course2, course3));
        Student student2 = new Student("Fernanda", List.of(course1,course2, course3));

        List<Student> listStudent = List.of(student1, student2);

        listStudent.stream()
            .flatMap(student -> student.getCourses().stream())
            .flatMap(course -> course.getModules().stream())
            .filter(module -> module.getName().length() > 3)
            .map(module -> module.getName().toUpperCase())
            .peek(moduleName -> System.out.println(moduleName))
            .forEach(System.out::println);

        listStudent.stream()
            .flatMap(student -> student.getCourses().stream())
            .filter(course -> course.getModules().size() > 2)
            .peek(courseName -> System.out.println(courseName.getName()))
            .collect(Collectors.toList());;


        listStudent.stream()
            .map(Student::getName)
            .peek(studentName -> System.out.println(studentName))
            .collect(Collectors.toList());
    }
}
