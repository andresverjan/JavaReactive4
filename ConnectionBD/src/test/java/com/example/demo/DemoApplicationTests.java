package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void testHelloMono() {

		Mono<String> helloMono = Mono.just("Hello");


		StepVerifier.create(helloMono)
				.expectNext("Hello")
				.expectComplete()
				.verify();
	}

	@Test
	void testStringFlux() {

		Flux<String> stringFlux = Flux.just("Hello", "Baeldung");


		StepVerifier.create(stringFlux)
				.expectNext("Hello")
				.expectNext("Baeldung")
				.expectComplete()
				.verify();
	}

	@Test
	void testHolaMundoFlux() {

		Flux<String> flux = Flux.just("Hola", "Mundo", "!");


		flux.subscribe(
				element -> System.out.println("Elemento recibido: " + element),
				error -> System.err.println("Error: " + error.getMessage()),
				() -> System.out.println("Secuencia completada")
		);
	}

	@Test
	void testNumberMono() {

		System.out.println("FLUJO MONO");

		Mono<Integer> numberMono = Mono.just(5);

		// Primer suscriptor
		numberMono.subscribe(
				number -> System.out.println("Primer suscriptor - Número multiplicado por 2: " + (number * 2))
		);

		// Segundo suscriptor
		numberMono.subscribe(
				number -> System.out.println("Segundo suscriptor - Número más 10: " + (number + 10))
		);

		// Tercer suscriptor
		numberMono.subscribe(
				number -> System.out.println("Tercer suscriptor - Número al cuadrado: " + (number * number))
		);
	}

	@Test
	void testNumberFlux() {

		System.out.println("FLUJO FLUX");

		Flux<Integer> numberFlux = Flux.just(1, 2, 3, 4, 5);

		// Primer suscriptor
		numberFlux.subscribe(
				number -> System.out.println("Primer suscriptor - Número multiplicado por 2: " + (number * 2))
		);

		// Segundo suscriptor
		numberFlux.subscribe(
				number -> System.out.println("Segundo suscriptor - Número más 10: " + (number + 10))
		);

		// Tercer suscriptor
		numberFlux.subscribe(
				number -> System.out.println("Tercer suscriptor - Número al cuadrado: " + (number * number))
		);
	}




}
