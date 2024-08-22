package com.example.Mono_Flux;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootTest
class MonoFluxApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void mono(){
		Mono<Integer> randomMono = Mono.fromSupplier(() -> new Random().nextInt(50) + 1);

		randomMono.subscribe(number -> {
			if (number > 10) {
				double sqrt = Math.sqrt(number);
				System.out.println("Subscriber 1 - Número: " + number + ", Raíz cuadrada: " + sqrt);
			} else {
				System.out.println("Subscriber 1 - Número: " + number + " (No se calcula la raíz cuadrada)");
			}
		});

		randomMono.subscribe(number -> {
			if (number > 20) {
				double sqrt = Math.sqrt(number);
				System.out.println("Subscriber 2 - Número: " + number + ", Raíz cuadrada: " + sqrt);
			} else {
				System.out.println("Subscriber 2 - Número: " + number + " (No se calcula la raíz cuadrada)");
			}
		});
	}

	@Test
	void flux(){
		List<String> countries = Arrays.asList("Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Paraguay");

		Flux<String> countryFlux = Flux.fromIterable(countries);

		countryFlux.subscribe(country -> {
			if (country.startsWith("C")) {
				System.out.println("Subscriber 1 - País que comienza con 'C': " + country);
			}
		});

		countryFlux.subscribe(country -> {
			if (country.length() > 5) {
				String vowels = country.replaceAll("[^AEIOUaeiou]", "");
				System.out.println("Subscriber 2 - País: " + country + ", Vocales: " + vowels);
			}
		});
	}

}
