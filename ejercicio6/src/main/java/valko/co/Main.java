package valko.co;

import valko.co.model.Course;
import valko.co.model.Student;
import valko.co.model.Module;

import java.util.List;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        List<Student> students = getStudents();

        students.stream()
                .flatMap(student -> student.getCourses().stream())
                .flatMap(course -> course.getModules().stream())
                .filter(module -> module.getName().length() > 3)
                .map(module -> module.getName().toUpperCase())
                .forEach(System.out::println);

        students.stream()
                .flatMap(student -> student.getCourses().stream())
                .filter(course -> course.getModules().size() > 2)
                .map(Course::getName)
                .forEach(System.out::println);

        students.stream()
                .map(Student::getName)
                .forEach(System.out::println);

    }

    private static List<Student> getStudents() {
        Module module1 = new Module("Math", "Mathematics Description");
        Module module2 = new Module("Physics", "Physics Description");
        Module module3 = new Module("Chemistry", "Chemistry Description");
        Module module4 = new Module("Biology", "Biology Description");
        Module module5 = new Module("History", "History Description");

        Course course1 = new Course("Science", List.of(module1, module2));
        Course course2 = new Course("Arts", List.of(module3, module4));
        Course course3 = new Course("Sports", List.of(module5));

        Student student1 = new Student("Alice", List.of(course1, course2, course3));
        Student student2 = new Student("Bob", List.of(course1, course3));
        Student student3 = new Student("Charlie", List.of(course2, course3));

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        return students;
    }
}