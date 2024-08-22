package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {

	}

	@Test
	void monoTest(){
		Mono<String> helloMono = Mono.just("Hello");
		StepVerifier.create(helloMono)
				.expectNext("Hello")
				.expectComplete()
				.verify();

	}

	@Test
	void fluxTest(){
		Flux<String> stringFlux = Flux.just("Hello", "Baeldung");
		StepVerifier.create(stringFlux)
				.expectNext("Hello")
				.expectNext("Baeldung")
				.expectComplete()
				.verify();
	}

}
