package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testFlux() {
		System.out.println("FLUJO FLUX");
		Flux<String> flux = Flux.just("Hola", "Mundo");

		flux.subscribe(
				element -> System.out.println("Primera suscripción: " + element + ", cantidad de letras: " + element.length()),
				error -> System.err.println("Error: " + error.getMessage()),
				() -> System.out.println("Secuencia completada en la primera suscripción")
		);

		flux.subscribe(
				element -> System.out.println("Segunda suscripción: " + element.toUpperCase() + ", cantidad de letras "+ element.length() +" * 2: " + element.length()*2),
				error -> System.err.println("Error: " + error.getMessage()),
				() -> System.out.println("Secuencia completada en la segunda suscripción")
		);

		flux.subscribe(
				element -> System.out.println("Tercera suscripción: " + element.toLowerCase() + ", cantidad de letras "+ element.length() +" / 2: " + element.length()/2),
				error -> System.err.println("Error: " + error.getMessage()),
				() -> System.out.println("Secuencia completada en la tercera suscripción")
		);
	}

	@Test
	void testMono() {
		System.out.println("FLUJO MONO");
		Mono<Integer> mono = Mono.just(8);
//		 Suscribirse al Mono y procesar el elemento emitido
		mono.subscribe(
				element -> System.out.println("Primera suscripción: " + element),
				error -> System.err.println("Error: " + error.getMessage()),
				() -> System.out.println("Secuencia completada en la primera suscripción")
		);

		mono.subscribe(
				element -> System.out.println("Segunda suscripción: " + element*2),
				error -> System.err.println("Error: " + error.getMessage()),
				() -> System.out.println("Secuencia completada en la segunda suscripción")
		);

		mono.subscribe(
				element -> System.out.println("Tercera suscripción: " + element/2),
				error -> System.err.println("Error: " + error.getMessage()),
				() -> System.out.println("Secuencia completada en la tercera suscripción")
		);
	}

//*******************************************************

	@Test
	void testMonoEjercicio1() {
		Mono<Integer> mono = Mono.just(new Random().nextInt(30));

		mono.filter(number -> number > 10)
				.subscribe(
						number -> System.out.println("Subscriber 1: " + Math.sqrt(number)),
						error -> System.err.println("Error: " + error.getMessage())
				);

		mono.filter(number -> number > 20)
				.subscribe(
						number -> System.out.println("Subscriber 2: " + Math.sqrt(number)),
						error -> System.err.println("Error: " + error.getMessage())
				);
		System.out.println("El flujo ha finalizado, el valor es el siguiente " + mono.block());
	}

	@Test
	void testFluxEjercicio2() {
		Flux<String> flux = Flux.just("Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Paraguay");

		flux.filter(pais -> pais.startsWith("C"))
				.subscribe(
						pais -> System.out.println("Subscriber 1: " + pais),
						error -> System.err.println("Error: " + error.getMessage())
				);

		flux.filter(pais -> pais.length() > 5)
				.subscribe(
						pais -> System.out.println("Subscriber 2: " + pais.replaceAll("[^aeiouAEIOU]", "")),
						error -> System.err.println("Error: " + error.getMessage())
				);

		System.out.println("El flujo ha finalizado");
	}

}
