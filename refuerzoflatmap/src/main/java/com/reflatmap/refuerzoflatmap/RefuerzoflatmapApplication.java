package com.reflatmap.refuerzoflatmap;

import com.reflatmap.refuerzoflatmap.entity.Course;
import com.reflatmap.refuerzoflatmap.entity.Module;
import com.reflatmap.refuerzoflatmap.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class RefuerzoflatmapApplication {

	public static void main(String[] args) {
		SpringApplication.run(RefuerzoflatmapApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo() {
		return (args) -> {
			List<Student> estudiantes = Arrays.asList(
				new Student("Alvaro",Arrays.asList(
					new Course("Biologia", Arrays.asList(
							new Module("Ecosistema",""),
							new Module("Celula",""),
							new Module("Animales Salvajes","")
					)),
					new Course("Anatomía", Arrays.asList(
							new Module("Sistema Nervioso",""),
							new Module("Sistema Circulatorio",""),
							new Module("Sistema Respiratorio","")
					))
				)),
				new Student("Matías",Arrays.asList(
						new Course("Matemáticas", Arrays.asList(
								new Module("Enteros",""),
								new Module("Decimales",""),
								new Module("Imaginarios","")
						)),
						new Course("Ingles", Arrays.asList(
								new Module("Verb To Be",""),
								new Module("Pronuntation","")
						))
				)),
				new Student("Emma",Arrays.asList(
						new Course("Anatomía", Arrays.asList(
								new Module("Sistema Nervioso",""),
								new Module("Sistema Circulatorio",""),
								new Module("Sistema Respiratorio","")
						)),
						new Course("Ingles", Arrays.asList(
								new Module("To Be",""),
								new Module("Pronuntation","")
						))
				))
			);

			System.out.println("------------Punto 1---------------");
			List<Module> modulosFiltrados = estudiantes.stream().flatMap(e -> e.getCourses().stream())
																 .flatMap(c -> c.getModules().stream())
																 .filter(m -> m.getName().length() > 7)
																 .collect(Collectors.toList());

			modulosFiltrados.stream().map(m->m.getName().toUpperCase()).forEach(System.out::println);

			System.out.println("-----------------------------");
			System.out.println("--------------Punto 2---------------");
			List<Course> cursosFiltrados = estudiantes.stream().flatMap(e -> e.getCourses().stream())
																	 .filter(c -> c.getModules().size() > 2)
																	.collect(Collectors.toList());

			cursosFiltrados.forEach(System.out::println);

			System.out.println("-----------------------------");
			System.out.println("-------------Punto 3----------------");
			List<String> allStudents = estudiantes.stream().map(Student::getName).collect(Collectors.toList());
			allStudents.forEach(System.out::println);

			System.out.println("-----------------------------");
		};
	}
}
