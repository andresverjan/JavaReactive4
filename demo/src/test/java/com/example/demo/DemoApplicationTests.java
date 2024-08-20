package com.example.demo;

import com.example.demo.DemoApplication;
import com.example.demo.Task;
import com.example.demo.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Random;
import java.util.logging.Logger;

@SpringBootTest(classes = DemoApplication.class)
class DemoApplicationTests {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void testAddAndReadTask() {
        // Inserta un nuevo Task y luego verifica que existe
        Mono<Task> savedTask = taskRepository.save(new Task(null, "Learn R2DBC", false))
                .doOnNext(task -> System.out.println("Registro creado: " + task));

        // Verifica que se ha guardado correctamente
        savedTask.as(StepVerifier::create)
                .expectNextMatches(task -> task.getId() != null && "Learn R2DBC".equals(task.getDescription()))
                .verifyComplete();

        // Lee el último registro creado
        StepVerifier.create(taskRepository.findByCompleted(false)
                        .filter(task -> "Learn R2DBC".equals(task.getDescription()))
                        .sort((task1, task2) -> Long.compare(task2.getId(), task1.getId()))  // Ordena por ID en orden descendente
                        .take(1)  // Toma solo el primer registro (el de mayor ID)
                        .doOnNext(task -> System.out.println("Último registro leído: " + task)))
                .expectNextMatches(task -> "Learn R2DBC".equals(task.getDescription()))
                .verifyComplete();
    }

    @Test
    void testAddAndReadTask2() {
        Flux<String> stringFlux = Flux.just("Hello", "Baeldung");
        StepVerifier.create(stringFlux)
                .expectNext("Hello")
                .expectNext("Baeldung")
                .expectComplete()
                .verify();
    }
    @Test
    void testAddAndReadTask3() {
        Flux<String> flux = Flux.just("Hola", "Mundo", "!");
        flux.subscribe(
                element -> System.out.println("Elemento recibido: " + element),
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Secuencia completada")
        );
    }

    @Test
    void testMonoWithDifferentSubscribers() {
        // Crear un Mono que emite un número aleatorio
        Mono<Integer> mono = Mono.fromSupplier(() -> new Random().nextInt(100));

        // Suscriptor 1: Imprimir el número
        mono.subscribe(value -> System.out.println("Suscriptor 1 - Número generado: " + value));

        // Suscriptor 2: Multiplicar el número por 2 y luego imprimirlo
        mono.map(value -> value * 2)
                .subscribe(value -> System.out.println("Suscriptor 2 - Número multiplicado por 2: " + value));

        // Suscriptor 3: Registrar si el número es par o impar
        mono.subscribe(value -> System.out.println(("Suscriptor 3 - El número es " + (value % 2 == 0 ? "par" : "impar"))));

        // Verificación usando StepVerifier (simulación de un número aleatorio conocido para la prueba)
        Mono<Integer> monoTest = Mono.just(42); // Suponiendo que el número aleatorio generado es 42
        StepVerifier.create(monoTest)
                .expectNext(42)
                .verifyComplete();
    }



    @Test
    void testFluxWithDifferentSubscribers() {

        // Crear un Flux que emite 5 números aleatorios
        Flux<Integer> flux = Flux.range(1, 5).map(i -> new Random().nextInt(100));

        // Suscriptor 1: Imprimir cada número
        flux.subscribe(value -> System.out.println("Suscriptor 1 - Número generado: " + value));

        // Suscriptor 2: Elevar cada número al cuadrado y luego imprimirlo
        flux.map(value -> value * value)
                .subscribe(value -> System.out.println("Suscriptor 2 - Número al cuadrado: " + value));

        // Suscriptor 3: Registrar si el número es mayor o menor que 50
        flux.subscribe(value -> System.out.println("Suscriptor 3 - El número es " + (value > 50 ? "mayor" : "menor") + " que 50"));

        // Verificación usando StepVerifier (simulación de números aleatorios conocidos para la prueba)
        Flux<Integer> fluxTest = Flux.just(42, 7, 85, 23, 56); // Suponiendo que los números aleatorios son estos
        StepVerifier.create(fluxTest)
                .expectNext(42, 7, 85, 23, 56)
                .verifyComplete();
    }

}
