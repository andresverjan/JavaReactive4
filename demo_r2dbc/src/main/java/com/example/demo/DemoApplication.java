package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Random;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		Mono<String> mono = Mono.just("Hola, Mundo!");
		// Suscribirse al Mono y procesar el elemento emitido
		mono.subscribe(
				element -> System.out.println("Elemento recibido: " + element),
					error -> System.err.println("Error: " + error.getMessage()),
					() -> System.out.println("Secuencia completada")
		);


		Flux<String> flux = Flux.just("Hola", "Mundo", "!");
		flux.subscribe(
				element -> System.out.println("Elemento recibido: " + element),
				error -> System.err.println("Error: " + error.getMessage()),
				() -> System.out.println("Secuencia completada")
		);

		Random objIntR = new Random();
		Flux<Integer> flux1 = Flux.just(objIntR.nextInt(1,10),objIntR.nextInt(1,100),objIntR.nextInt(1,1000));
		flux1.subscribe(
				element -> System.out.println("Elemento recibido flux: " + element),
				error -> System.err.println("Error: " + error.getMessage()),
				() -> System.out.println("Secuencia completada")
		);

		flux1.subscribe(
				element -> System.out.println("Elemento recibido flux: " + element*2),
				error -> System.err.println("Error: " + error.getMessage()),
				() -> System.out.println("Secuencia completada")
		);


		//Tarea

		Random objIntR1 = new Random();
		//Mono<Integer> mono1 =Mono.just(objIntR1.nextInt(1,50));
		Mono<Integer> mono1 =Mono.defer(() -> Mono.just(objIntR1.nextInt(1,100)));
		mono1.subscribe(
				integer -> {
					double sqrt=0;
					if(integer>10){
						sqrt = Math.sqrt(integer);
					}
					System.out.println("Elemento random recibido: "+integer);
					System.out.println("raiz cuadrada: "+sqrt);
					},
				error -> System.err.println("Error: " + error.getMessage()),
				() -> System.out.println("Secuencia completada")
		);


		System.out.println("**********************Tarea*******************************");
		System.out.println("**********************#1*******************************");
		//Punto #1
		mono1.filter(integer -> integer > 10).subscribe(integer -> System.out.println("raiz cuadrada > 10 en de "+integer+" mono : "+Math.sqrt(integer)));

		System.out.println("**********************#2*******************************");
		//Punto #2
		mono1.filter(integer -> integer > 20).subscribe(integer -> System.out.println("raiz cuadrada > 20 en de "+integer+" mono : "+Math.sqrt(integer)));


		Flux<String> fluxStr = Flux.just("Argentina","Bolivia","Brasil","Colombia","Paraguay");

		//Punto #3
		System.out.println("**********************#3*******************************");
		fluxStr.filter(i-> i.startsWith("C")).subscribe(System.out::println);

		//Punto #4
		System.out.println("**********************#4*******************************");
		fluxStr.filter(i->i.length()>5).subscribe(i->System.out.println(i +": "+ i.toLowerCase().replaceAll("[^aeiou]","")));
		System.out.println("**********************#4.1*******************************");
		fluxStr.filter(i->i.length()>5).doOnNext(i -> System.out.println(i+" #2: "+i.toLowerCase().replaceAll("[^aeiou]",""))).subscribe();


		// Crear un Mono que emite el valor actual del sistema cuando se suscribe
		Mono<String> deferMono = Mono.defer(() -> Mono.just(getCurrentTime()))
				.subscribeOn(Schedulers.parallel());
		// Suscribirse al Mono y procesar el valor emitido
		deferMono.subscribe(System.out::println);
		deferMono.subscribe(System.out::println);


		Mono<Double> monoSupplier = Mono.fromSupplier(()->{
			double randomValue = Math.random();
			return randomValue;
		});

		Mono<Double> monoSupplier1 = Mono.fromSupplier(Math::random);

		monoSupplier.subscribe(System.out::println);
		monoSupplier.subscribe(System.out::println);

		Mono<String> fileContentMono = Mono.fromCallable(()->readFile("example"));
		fileContentMono.subscribe(
				content->System.out.println("Contenido del archivo: "+content),
				error-> System.err.println("Error al leer el archivo: "+error.getMessage())
		);

		fileContentMono.subscribe(
				content->System.out.println("Contenido del archivo: "+content),
				error-> System.err.println("Error al leer el archivo: "+error.getMessage())
		);

	}

	private static String readFile(String fileName) throws InterruptedException {
		Thread.sleep(1000);
		return "Hello World "+fileName + " Horal actual: "+getCurrentTime();
	}

	// Método para obtener la hora actual
	private static String getCurrentTime() {
		return "Hora actual: " + System.currentTimeMillis();
	}





}
