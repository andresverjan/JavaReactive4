import java.util.Arrays;
import java.util.List;

class Student {
    private String name;
    private List<Course> courses;

    public Student(String name, List<Course> courses) {
        this.name = name;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public List<Course> getCourses() {
        return courses;
    }
}

class Course {
    private String name;
    private List<Module> modules;

    public Course(String name, List<Module> modules) {
        this.name = name;
        this.modules = modules;
    }

    public String getName() {
        return name;
    }

    public List<Module> getModules() {
        return modules;
    }
}

class Module {
    private String name;
    private String description;

    public Module(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

public class Main {
    public static void main(String[] args) {
        // Crear módulos
        Module module1 = new Module("Math", "Basic Mathematics");
        Module module2 = new Module("History", "World History");
        Module module3 = new Module("Physics", "Introduction to Physics");
        Module module4 = new Module("Bio", "Biology Basics");
        Module module5 = new Module("Chemistry", "Chemistry Intro");

        // Crear cursos
        Course course1 = new Course("Science", Arrays.asList(module1, module2, module3));
        Course course2 = new Course("Literature", Arrays.asList(module2, module3));
        Course course3 = new Course("Math", Arrays.asList(module1, module4));
        Course course4 = new Course("Physics", Arrays.asList(module3, module5));

        // Crear estudiantes
        Student student1 = new Student("John", Arrays.asList(course1, course2, course3));
        Student student2 = new Student("Jane", Arrays.asList(course2, course3, course4));
        Student student3 = new Student("Alice", Arrays.asList(course1, course3, course4));

        List<Student> students = Arrays.asList(student1, student2, student3);

        // 2. Listar todos los módulos con nombre mayor a 3 caracteres y retornar el nombre en mayúsculas
        students.stream()
                .flatMap(student -> student.getCourses().stream())
                .flatMap(course -> course.getModules().stream())
                .filter(module -> module.getName().length() > 3)
                .map(module -> module.getName().toUpperCase())
                .forEach(System.out::println);

        // 3. Listar todos los cursos que tengan más de 2 módulos
        students.stream()
                .flatMap(student -> student.getCourses().stream())
                .filter(course -> course.getModules().size() > 2)
                .map(Course::getName)
                .forEach(System.out::println);

        // 4. Listar todos los estudiantes
        students.stream()
                .map(Student::getName)
                .forEach(System.out::println);
    }
}

