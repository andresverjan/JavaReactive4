package com.bancolombia.demomonoflux;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.StringJoiner;

@SpringBootApplication
public class DemomonofluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemomonofluxApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo() {

		return (args) -> {

			//--------------- Actividad Mono ------------------
			Random randomico = new Random();
			Mono<Integer> mono = Mono.just(randomico.nextInt(30) + 1 );

			mono.subscribe( element -> {
						System.out.println("------------Suscribe 1---------------");
				    	System.out.println("Elemento recibido: " + element);

						if (element > 10) {
							System.out.println("Elemento mayor a 10");
							System.out.println("La raíz cuadrada es : " + Math.sqrt(element));
						} else {
							System.out.println("Elemento menor o igual a 10");
						}

					}, error ->
					System.err.println("Error: " + error.getMessage()), () ->
					System.out.println("Secuencia completada")
			);

			mono.subscribe( element -> {
						System.out.println("------------Suscribe 2---------------");
						System.out.println("Elemento recibido: " + element);

						if (element > 20) {
							System.out.println("Elemento mayor a 20");
							System.out.println("La raíz cuadrada es : " + Math.sqrt(element));
						} else {
							System.out.println("Elemento menor o igual a 20");
						}

					}, error ->
							System.err.println("Error: " + error.getMessage()), () ->
							System.out.println("Secuencia completada")
			);

			//--------------- Actividad Flux ------------------
			Flux<String> flux = Flux.just("Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Paraguay");
			flux.subscribe(new Subscriber<String>() {
							   StringJoiner paises = new StringJoiner(", ");

							   @Override
							   public void onSubscribe(Subscription s) {
								   s.request(Long.MAX_VALUE);
								   System.out.println("====== Suscribe 1 ======");
							   }

							   @Override
							   public void onNext(String s) {
								   if (Character.toUpperCase(s.charAt(0)) == 'C') {
									   paises.add(s);
								   }
							   }

							   @Override
							   public void onError(Throwable t) {
								   t.printStackTrace();
							   }

							   @Override
							   public void onComplete() {
								   System.out.println(paises.toString());
								   System.out.println("====== Execution Completed ======");
							   }
						   }
			);

			flux.subscribe(new Subscriber<String>() {

							   String vocales = "aeiou";

							   @Override
							   public void onSubscribe(Subscription s) {
								   s.request(Long.MAX_VALUE);
								   System.out.println("====== Suscribe 2 ======");
							   }

							   @Override
							   public void onNext(String s) {
								   StringBuilder builder = new StringBuilder();

								   var longitud = s.length();

								   if (longitud > 5) {
									   for (char c : s.toLowerCase().toCharArray()) {
										   if ("aeiou".indexOf(c) != -1) {
											   builder.append(c);
										   }
									   }
									   System.out.println(builder);
								   }
							   }

							   @Override
							   public void onError(Throwable t) {
								   t.printStackTrace();
							   }

							   @Override
							   public void onComplete() {
								   System.out.println("====== Execution Completed ======");
							   }
						   }
			);
		};
	}
}
