package com.reactiveCourse.streamReactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class StreamReactiveApplication {

	public static void main(String[] args) {
		Flux<Integer> numbers = Flux.just(1, 2, 0, 4, 5)

				.map(n -> {

					if (n == 0) {

						throw new ArithmeticException("Division by zero");

					}

					return 10 / n;

				})

				.doOnError(e -> System.out.println("Error occurred: " + e.getMessage()))

				.onErrorResume(e -> {

					System.out.println("Handling error: " + e.getMessage());

					return Flux.just(-1, -2, -3);

				})

				.onErrorReturn(
						99
				);

		numbers.subscribe(

				value -> System.out.println("Received: " + value),

				error -> System.out.println("Error in subscription: " + error.getMessage()),

				() -> System.out.println("Completed")

		);
	}

}
