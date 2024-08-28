package com.example.demo;

import com.example.demo.model.Employee;
import com.example.demo.model.Person;
import com.example.demo.model.Persona;
import org.reactivestreams.Subscriber;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

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

		//*****************************************

		System.out.println("*******************Peek*******************************");

		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

		List<Integer> doubledNumbers = numbers.stream()
				.peek(num -> System.out.println("Número original: " + num))
				.map(num -> num * 2)
				.peek(doubledNum -> System.out.println("Número doblado: " + doubledNum))
				.map(num -> num + 1)
				.peek(doubledNum2 -> System.out.println("Número + 1: " + doubledNum2))
				.toList();

		System.out.println("Números doblados: " + doubledNumbers);

		System.out.println("*******************Sorted*******************************");

		List<String> nombres = Arrays.asList("Juan", "Ana", "Carlos", "María");

		nombres.stream()
				.sorted() // Ordena en orden natural (alfabético en este caso)
				.forEach(System.out::println);

		System.out.println("*******************Collect*******************************");

/*		// Accumulate names into a List
		List<String> list = people.stream().map(Person::getName).collect(Collectors.toList());

		// Accumulate names into a TreeSet
		Set<String> set = people.stream().map(Person::getName).collect(Collectors.toCollection(TreeSet::new));

		// Convert elements to strings and concatenate them, separated by commas
		String joined = things.stream()
				.map(Object::toString)
				.collect(Collectors.joining(", "));

		// Compute sum of salaries of employee
		int total = employees.stream()
				.collect(Collectors.summingInt(Employee::getSalary));

		// Group employees by department
		Map<Department, List<Employee>> byDept
				= employees.stream()
				.collect(Collectors.groupingBy(Employee::getDepartment));*/


		System.out.println("*******************Manipulación de flujos basica*******************************");

		Persona persona1 = new Persona("Juan", "Pérez", "123456789", 30, "Aries", Arrays.asList("a","b","c"));
		Persona persona2 = new Persona("María", "Gómez", "987654321", 25, "Virgo", Arrays.asList("a","b","c"));
		Persona persona3 = new Persona("Carlos", "Martínez", "555444333", 40, "Capricornio", Arrays.asList("a","b","c"));
		Persona persona4 = new Persona("Laura", "Rodríguez", "111222333", 35, "Tauro", Arrays.asList("a","b","c"));
		Persona persona5 = new Persona("Pedro", "Sánchez", "999888777", 28, "Leo", Arrays.asList("a","b","c"));
		Persona persona6 = new Persona("Ana", "Fernández", "666777888", 22, "Acuario", Arrays.asList("a","b","c"));
		Persona persona7 = new Persona("David", "López", "333222111", 45, "Cáncer", Arrays.asList("a","b","c"));
		Persona persona8 = new Persona("Sofía", "Díaz", "777666555", 32, "Géminis", Arrays.asList("a","b","c"));
		Persona persona9 = new Persona("Javier", "Hernández", "888999000", 27, "Escorpio", Arrays.asList("a","b","c"));
		Persona persona10 = new Persona("Elena", "García", "112233445", 33, "Libra", Arrays.asList("a","b","c"));
		Persona persona11 = new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis", Arrays.asList("a","b","c"));
		Persona persona12 = new Persona("Rosa", "Jiménez", "998877665", 29, "Piscis", Arrays.asList("a","b","c"));
		Persona persona13 = new Persona("Luis","Hurtado","3045797824",31,"Sagitario", Arrays.asList("a","b","c"));


		/**
		 * 1. Crear un Flux a partir de la lista de personas.
		 */
		Flux<Persona> personFlux = Flux.just(persona1,persona2,persona3,persona4,persona5,persona6,persona7,persona8,
				persona9,persona10,persona11,persona12);

		/**
		 * * /2. Filtrar las personas mayores de 30 años utilizando filter().
		 */
		personFlux.filter(persona -> persona.getEdad()>30).doOnNext(System.out::println).subscribe();

		/**
		 * * 3. Mostrar los nombres de las personas mayores de 30 años utilizando map(), subscribe() y filter()
		 */
		personFlux.filter(persona -> persona.getEdad()>30).map(Persona::getNombre).subscribe(System.out::println);

		/**
		 * * 4. Crear un Mono con la primera persona de la lista.
		 */
		Mono<Persona> personaMono = Mono.just(persona1);

		/**
		 * * 5. Mostrar el nombre y apellido de la persona del Mono utilizando flatMap() y subscribe().
		 */
		personaMono.map(Persona::getNombre).subscribe();
		persona1.getDirecciones().stream().flatMap(p -> Arrays.stream(p.split(""))).collect(Collectors.toList());

		/**
		 * * 6. Agrupar a las personas por signo del zodiaco utilizando groupBy(), flatMap() y collectList(). Luego,
		 * * mostrar el signo y la cantidad de personas para cada grupo.
		 */
		personFlux.groupBy(Persona::getSigno).flatMap(Flux::count).collectList().subscribe(System.out::println);


		//Calls

		obtenerPersonasPorEdad(32).subscribe();
		obtenerPersonasPorSigno("Acuario").subscribe();
		obtenerPersonaPorTelefono("555444333").subscribe(p->System.out.println(p));
		agregarPersona(new Persona("Jose","Hurtado","3015797824",26,"Sagitario", Arrays.asList("a","b","c"))).subscribe();
		eliminarPersona(new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis", Arrays.asList("a","b","c"))).subscribe();
	}


	/**
	 * 7. Crear una función obtenerPersonasPorEdad(int edad) que reciba una edad como parámetro y devuelva un Flux con
	 * 	// las personas que tengan esa edad. (Hacer uso de peek)
	 */
	private static Flux<Persona> obtenerPersonasPorEdad(int edad){

		return Flux.fromIterable(personas)
				.filter(persona -> persona.getEdad()==edad)
				.doOnNext(System.out::println);

	}

	/**
	 *
	 * @param signo
	 * @return
	 *  8. Crear una función obtenerPersonasPorSigno(String signo) que reciba un signo del zodiaco como parámetro y devuelva
	 * 	 un Flux con las personas que tengan ese signo. (Hacer uso de peek)*
	 */
	private static Flux<Persona> obtenerPersonasPorSigno(String signo){
		return Flux.fromIterable(personas)
				.filter(persona->persona.getSigno().equalsIgnoreCase(signo))
				.doOnNext(System.out::println);
	}


	/**
	 * *
	 * @param telefono
	 * @return
	 * 9. Crear una función obtenerPersonaPorTelefono(String telefono) que reciba un número de teléfono como parámetro y
	 * 	 }devuelva un Mono con la persona que tenga ese número de teléfono. Si no se encuentra, devolver un Mono vacío. (Hacer uso de peek)
	 */

	private static Mono<Persona> obtenerPersonaPorTelefono(String telefono){

		return Flux.fromIterable(personas)
				.filter(persona -> persona.getTelefono().equalsIgnoreCase(telefono))
				.singleOrEmpty();
	}


	/**
	 * *
	 * @param persona
	 * @return
	 *  10. Crear una función agregarPersona(Persona persona) que reciba una persona como parámetro y la agregue a la lista de personas.
	 * 	Devolver un Mono con la persona agregada. (Hacer uso de peek)
	 */
	private static Mono<Persona> agregarPersona(Persona persona){
		List<Persona> personaList = new ArrayList<>(personas);
		return Mono.fromCallable(()->{
			personaList.add(persona);
			return persona;
		}).doOnNext(p -> System.out.println("Persona a agregar: " + p));
	}

	//

	/**
	 * *
	 * @param persona
	 * @return
	 * 11. Crear una función eliminarPersona(Persona persona) que reciba una persona como parámetro y la elimine de la
	 * 	// lista de personas. Devolver un Mono con la persona eliminada.
	 */
	private static Mono<Persona> eliminarPersona( Persona persona){
		List<Persona> personaList = new ArrayList<>(personas);
		return Mono.fromCallable(()->{
			boolean removed = personaList.remove(persona);
			return removed? persona:null;
		}).filter(personaEliminada -> personaEliminada!=null)
				.doOnNext(p->System.out.println("Persona eliminada de la lista: " + p));

	}

	private static final List<Persona> personas = List.of(
			new Persona("Juan", "Pérez", "123456789", 30, "Aries", Arrays.asList("a","b","c")),
			new Persona("María", "Gómez", "987654321", 25, "Virgo", Arrays.asList("a","b","c")),
			new Persona("Carlos", "Martínez", "555444333", 40, "Capricornio", Arrays.asList("a","b","c")),
			new Persona("Laura", "Rodríguez", "111222333", 35, "Tauro", Arrays.asList("a","b","c")),
			new Persona("Pedro", "Sánchez", "999888777", 28, "Leo", Arrays.asList("a","b","c")),
			new Persona("Ana", "Fernández", "666777888", 22, "Acuario", Arrays.asList("a","b","c")),
			new Persona("David", "López", "333222111", 45, "Cáncer", Arrays.asList("a","b","c")),
			new Persona("Sofía", "Díaz", "777666555", 32, "Géminis", Arrays.asList("a","b","c")),
			new Persona("Javier", "Hernández", "888999000", 27, "Escorpio", Arrays.asList("a","b","c")),
			new Persona("Elena", "García", "112233445", 32, "Libra", Arrays.asList("a","b","c")),
			new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis", Arrays.asList("a","b","c")),
			new Persona("Rosa", "Jiménez", "998877665", 29, "Piscis", Arrays.asList("a","b","c")),
			new Persona("Luis","Hurtado","3045797824",31,"Sagitario", Arrays.asList("a","b","c"))
	);

	private static String readFile(String fileName) throws InterruptedException {
		Thread.sleep(1000);
		return "Hello World "+fileName + " Horal actual: "+getCurrentTime();
	}

	// Método para obtener la hora actual
	private static String getCurrentTime() {
		return "Hora actual: " + System.currentTimeMillis();
	}





}
