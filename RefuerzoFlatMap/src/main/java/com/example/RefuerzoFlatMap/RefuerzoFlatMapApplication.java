package com.example.RefuerzoFlatMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class RefuerzoFlatMapApplication {

	public static void main(String[] args) {

		Module mathModule = new Module("Math", "Introduction to Algebra");
		Module physicsModule = new Module("Physics", "Classical Mechanics");
		Module litModule = new Module("Literature", "World Literature Overview");
		Module chemModule = new Module("Chemistry", "Organic Chemistry");
		Module bioModule = new Module("Biology", "Cell Biology");

		Course scienceCourse = new Course("Science", Arrays.asList(mathModule, physicsModule, chemModule));
		Course artsCourse = new Course("Arts", Arrays.asList(litModule));
		Course bioCourse = new Course("Biology", Arrays.asList(bioModule, chemModule));
		Course engCourse = new Course("Engineering", Arrays.asList(physicsModule, mathModule));

		Student student1 = new Student("Alice", Arrays.asList(scienceCourse, artsCourse, bioCourse));
		Student student2 = new Student("Bob", Arrays.asList( engCourse, artsCourse));
		Student student3 = new Student("Charlie", Arrays.asList(engCourse, artsCourse));

		List<Student> students = List.of(student1, student2, student3);


		System.out.println("FlatMap nombre longitud mayor a 3");
		Flux.fromIterable(students)
				.flatMap(student -> Flux.fromIterable(student.getCourses()))
				.flatMap(course -> Flux.fromIterable(course.getModules()))
				.filter(module -> module.getName().length() > 3)
				.map(module -> module.getName().toUpperCase())
				.subscribe(System.out::println);

		System.out.println(" ");
		System.out.println("FlatMap mas de 2 modulos");
		Flux.fromIterable(students)
				.flatMap(student -> Flux.fromIterable(student.getCourses()))
				.filter(course -> course.getModules().size() > 2)
				.map(Course::getName)
				.subscribe(System.out::println);

		System.out.println(" ");
		System.out.println("Map listar estudiantes");
		Flux.fromIterable(students)
				.map(Student::getName)
				.subscribe(System.out::println);

	}
}
