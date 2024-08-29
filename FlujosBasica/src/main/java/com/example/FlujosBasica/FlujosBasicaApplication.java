package com.example.FlujosBasica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootApplication
public class FlujosBasicaApplication {

	public static void main(String[] args) {

		// Crear un Flux a partir de la lista de personas.
		Flux<Persona> personaFlux = Flux.fromIterable(personas);

		// 2. Filtrar las personas mayores de 30 años utilizando filter().
		// 3. Mostrar los nombres de las personas mayores de 30 años utilizando map(), subscribe() y filter()

		personaFlux
				.filter(persona -> persona.getEdad() > 30)
				.map(Persona::getNombre)
				.subscribe(nombre -> System.out.println("Nombre mayor de 30: " + nombre));

		// 4. Crear un Mono con la primera persona de la lista.
		Mono<Persona> personaMono = Mono.just(personas.get(0));
		personaMono
				.subscribe(nombreCompleto -> System.out.println("Nombre completo del Mono: " + nombreCompleto));

		// 5. Mostrar el nombre y apellido de la persona del Mono utilizando flatMap() y subscribe().
		personaMono
				.flatMap(persona -> Mono.just(persona.getNombre() + " " + persona.getApellido()))
				.subscribe(nombreCompleto -> System.out.println("Nombre completo del Mono: " + nombreCompleto));

		//6
		personaFlux
				.groupBy(Persona::getSigno)
				.flatMap(grupo -> grupo.collectList()
						.doOnNext(lista -> System.out.println("Signo: " + grupo.key() + " - Cantidad: " + lista.size()))) // Mostrar el signo y la cantidad de personas
				.subscribe();

		// 7. Crear una función obtenerPersonasPorEdad(int edad) que reciba una edad como parámetro y devuelva un Flux con las personas que tengan esa edad.
		obtenerPersonasPorEdad(30)
				.subscribe(persona -> System.out.println("Persona de 30 años: " + persona));

		// 8. Crear una función obtenerPersonasPorSigno(String signo) que reciba un signo del zodiaco como parámetro y devuelva un Flux con las personas que tengan ese signo. (Hacer uso de peek)
		obtenerPersonasPorSigno("Leo")
				.subscribe(persona -> System.out.println("Persona con signo Leo: " + persona));

		//9.
		obtenerPersonaPorTelefono("123456789")
				.doOnNext(persona -> System.out.println("Persona encontrada: " + persona))
				.switchIfEmpty(Mono.fromRunnable(() -> System.out.println("No se encontró la persona con ese teléfono")))
				.subscribe();


		// 10. Función agregarPersona
		Persona nuevaPersona = new Persona("Jorge", "Alonso", "000111222", 29, "Sagitario");
		agregarPersona(nuevaPersona)
				.subscribe(persona -> System.out.println("Persona agregada: " + persona));

		// 11. Función eliminarPersona
		eliminarPersona(nuevaPersona)
				.subscribe(persona -> System.out.println("Persona eliminada: " + persona));

	}


	public static Flux<Persona> obtenerPersonasPorEdad(int edad) {
		return Flux.fromIterable(personas)
				.filter(persona -> persona.getEdad() == edad);
	}

	public static Flux<Persona> obtenerPersonasPorSigno(String signo) {
		return Flux.fromIterable(personas)
				.filter(persona -> persona.getSigno().equalsIgnoreCase(signo));
	}


	public static Mono<Persona> agregarPersona(Persona persona) {
		return Mono.just(persona);

	}

	public static Mono<Persona> eliminarPersona(Persona persona) {
		return Mono.just(persona);

	}

	public static Mono<Persona> obtenerPersonaPorTelefono(String telefono) {
		return Flux.fromIterable(personas)
				.filter(persona -> persona.getTelefono().equals(telefono))
				.next()
				.doOnNext(persona -> System.out.println("Persona con teléfono " + telefono + " encontrada: " + persona))
				.switchIfEmpty(Mono.empty());
	}
	private static final List<Persona> personas = List.of(
			new Persona("Juan", "Pérez", "123456789", 30, "Aries"),
			new Persona("María", "Gómez", "987654321", 25, "Virgo"),
			new Persona("Carlos", "Martínez", "555444333", 40, "Capricornio"),
			new Persona("Laura", "Rodríguez", "111222333", 35, "Tauro"),
			new Persona("Pedro", "Sánchez", "999888777", 28, "Leo"),
			new Persona("Ana", "Fernández", "666777888", 22, "Acuario"),
			new Persona("David", "López", "333222111", 45, "Cáncer"),
			new Persona("Sofía", "Díaz", "777666555", 32, "Géminis"),
			new Persona("Javier", "Hernández", "888999000", 27, "Escorpio"),
			new Persona("Elena", "García", "112233445", 33, "Libra"),
			new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis"),
			new Persona("Rosa", "Jiménez", "998877665", 29, "Sagitario")
	);

}
