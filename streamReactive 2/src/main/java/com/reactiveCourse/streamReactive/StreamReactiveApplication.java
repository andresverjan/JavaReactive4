package com.reactiveCourse.streamReactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

public class StreamReactiveApplication {

	public static void main(String[] args) {
		GeneradorPersonas personas = new GeneradorPersonas();
		personas.crearListaPersonas();
		personas.obtenerPersonas("Filtro de personas mayores de 30")
				.filter(p -> p.getEdad() > 30)
				.subscribe(System.out::println);
		personas.obtenerPersonas("filtro de personas mayores de 30 con nombre y apellido")
				.filter(p -> p.getEdad() > 30)
				.map(p -> p.getNombre())
				.subscribe(System.out::println);
		personas.obtenerPersonas("Mono de primer usuario")
				.next()
				.flatMap(p -> {
					return Mono.just(p.getNombre() + " " + p.getApellido());
				})
				.subscribe(System.out::println);
		personas.obtenerPersonas("Agrupacion por signo")
				.groupBy(Persona::getSigno)
				.flatMap(group ->
						group.collectList()
								.map(personasEnGrupo ->
										"Signo: " + group.key() + " Personas en grupo: " + personasEnGrupo.size()
								)
				)
				.subscribe();
		personas.obtenerPersonasPorEdad(30)
				.subscribe();
		personas.obtenerPersonasPorSigno("Cancer")
				.subscribe();
		personas.obtenerPersonasPorTelefono("998877665")
				.subscribe();
		personas.eliminarPersona(Persona.builder()//persona 12
				.nombre("Rosa")
				.apellido("Jimenez")
				.telefono("998877665")
				.edad(29)
				.signo("Sagitario")
				.build());
	}

}
