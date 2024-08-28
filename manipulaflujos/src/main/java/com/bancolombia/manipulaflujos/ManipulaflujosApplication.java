package com.bancolombia.manipulaflujos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@SpringBootApplication
public class ManipulaflujosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManipulaflujosApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo() {
		return (args) -> {

			List<Persona> personas = new ArrayList<>();

			Persona persona1 = new Persona("Juan", "Pérez", "123456789", 30, "Aries", "juan@correoa.com;juan@correob.com");
			Persona persona2 = new Persona("María", "Gómez", "987654321", 25, "Virgo", "maria@correoa.com;maria@correob.com");
			Persona persona3 = new Persona("Carlos", "Martínez", "555444333", 40, "Capricornio", "carlos@correoa.com;carlos@correob.com");
			Persona persona4 = new Persona("Laura", "Rodríguez", "111222333", 35, "Tauro", "Laura@correoa.com;Laura@correob.com");
			Persona persona5 = new Persona("Pedro", "Sánchez", "999888777", 28, "Leo", "Pedro@correoa.com;Pedro@correob.com");
			Persona persona6 = new Persona("Ana", "Fernández", "666777888", 22, "Acuario", "Ana@correoa.com;Ana@correob.com");
			Persona persona7 = new Persona("David", "López", "333222111", 45, "Cáncer", "David@correoa.com;David@correob.com");
			Persona persona8 = new Persona("Sofía", "Díaz", "777666555", 32, "Géminis", "Sofia@correoa.com;Sofia@correob.com");
			Persona persona9 = new Persona("Javier", "Hernández", "888999000", 27, "Escorpio", "Javier@correoa.com;Javier@correob.com");
			Persona persona10 = new Persona("Elena", "García", "112233445", 33, "Libra", "Elena@correoa.com;Elena@correob.com");
			Persona persona11 = new Persona("Pablo", "Muñoz", "554433221", 40, "Escorpio", "Pablo@correoa.com;Pablo@correob.com");
			Persona persona12 = new Persona("Rosa", "Jiménez", "998877665", 29, "Sagitario", "Rosa@correoa.com;Rosa@correob.com");

			personas.add(persona1);
			personas.add(persona2);
			personas.add(persona3);
			personas.add(persona4);
			personas.add(persona5);
			personas.add(persona6);
			personas.add(persona7);
			personas.add(persona8);
			personas.add(persona9);
			personas.add(persona10);
			personas.add(persona11);
			personas.add(persona12);

			//Punto 1
			System.out.println("------- Punto 1 --------");
			Flux<Persona> flux = Flux.fromIterable(personas);
			flux.doOnNext(System.out::println).subscribe();
			System.out.println("----------------------");

			//Punto 2
			System.out.println("------- Punto 2 --------");
			personas.stream().filter(x -> x.getEdad() > 30).forEach(System.out::println);
			System.out.println("----------------------");

			//Punto 3
			System.out.println("------- Punto 3 --------");
			flux.filter(x -> x.getEdad() > 30).map(Persona::getNombre).doOnNext(System.out::println).subscribe();
			System.out.println("----------------------");

			//Punto4
			System.out.println("------- Punto 4 --------");
			Mono<Persona> monoPersona = Mono.just(personas.stream().findFirst().orElse(new Persona()));
			monoPersona.doOnNext(System.out::println).subscribe();
			System.out.println("----------------------");

			//Punto 5
			System.out.println("------- Punto 5 --------");
            /*monoPersona.doOnNext(x -> System.out.println(x.getNombre() + " " + x.getApellido())).subscribe();
            monoPersona.doOnNext(x -> System.out.println(Arrays.stream(x.getCorreos().split(";")).collect(Collectors.toList()))).subscribe();*/
			System.out.println(personas.stream().flatMap(p -> Arrays.stream(p.getCorreos().split(";"))).collect(Collectors.toList()));
			System.out.println("----------------------");

			//Punto 6
			System.out.println("------- Punto 6 --------");
			Map<String, List<Persona>> agrupacion = personas.stream().collect(groupingBy(Persona::getSigno));
			List<?> a = agrupacion.values().stream().flatMap(Collection::stream).toList(); //.forEach((x, y) -> y.stream().flatMap(z->z.getNombre()+z.getEdad()).collect(Collectors.toList()));

			System.out.println(a);
			agrupacion.forEach((x, y) -> System.out.println("Signo: " + x + ", Total: " + y.size()));
			System.out.println("----------------------");

			GestionPersona gPersona = new GestionPersona(personas);

			//Punto 7
			System.out.println("------- Punto 7 --------");
			gPersona.obtenerPersonasPorEdad(40).doOnNext(System.out::println).subscribe();
			System.out.println("----------------------");

			//Punto 8
			System.out.println("------- Punto 8 --------");
			gPersona.obtenerPersonasPorSigno("Aries").doOnNext(System.out::println).subscribe();

			//Punto 9
			System.out.println("------- Punto 9 --------");
			gPersona.obtenerPersonaPorTelefono("777666555").doOnNext(System.out::println).subscribe();
			System.out.println("----------------------");

			//Punto 10
			System.out.println("------- Punto 10 --------");
			Persona personaNueva = new Persona("Emma", "Rada", "7454741", 29, "Acuario", "emma@correo.com");
			gPersona.agregarPersona(personaNueva).doOnNext(System.out::println).subscribe();
			System.out.println("----------------------");

			//Punto 11
			System.out.println("------- Punto 11 --------");
			gPersona.eliminarPersona(persona12).doOnNext(System.out::println).subscribe();
			System.out.println("----------------------");
		};
	}


	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	class Persona {
		private String nombre;
		private String apellido;
		private String telefono;
		private int edad;
		private String signo;
		private String correos;
	}

	class GestionPersona {
		List<Persona> personas;

		public GestionPersona(List<Persona> personas) {
			this.personas = personas;
		}

		public Flux<List<Persona>> obtenerPersonasPorEdad(int edad) {
			return Flux.just(personas.stream().filter(x -> x.getEdad() == edad).collect(Collectors.toList()));
		}

		public Flux<List<Persona>> obtenerPersonasPorSigno(String signo) {
			return Flux.just(personas.stream().filter(x -> x.getSigno().equals(signo)).collect(Collectors.toList()));
		}

		public Mono<Persona> obtenerPersonaPorTelefono(String telefono) {
			return Mono.just(personas.stream().filter(x -> x.getTelefono().equals(telefono)).findFirst().orElse(new Persona()));
		}

		public Mono<Persona> agregarPersona(Persona persona) {
			this.personas.add(persona);
			return Mono.just(persona);
		}

		public Mono<Persona> eliminarPersona(Persona persona) {
			boolean removido = this.personas.removeIf(p -> (p.getNombre() + p.getApellido()).equals(persona.getNombre() + persona.getApellido()));
			return removido ? Mono.just(persona) : Mono.just(new Persona());
		}
	}
}