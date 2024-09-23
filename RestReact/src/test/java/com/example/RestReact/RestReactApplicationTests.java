package com.example.RestReact;

import com.example.RestReact.model.PersonEntity;
import com.example.RestReact.repository.PersonRepository;
import com.example.RestReact.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class RestReactApplicationTests {
	private WebTestClient webTestClient;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void contextLoads() {
	}

	@Mock
	private PersonRepository personRepository;
	@InjectMocks
	private PersonService personService;

	@Test
	void testFindAll(){
		PersonEntity person1 = new PersonEntity(1,"Angel",30);
		PersonEntity person2 = new PersonEntity(2,"Carlos",50);

		Mockito.when(personRepository.findAll()).thenReturn(Flux.just(person1,person2));
		StepVerifier.create(personService.getAll())
				.expectNext(person1)
				.expectNext(person2)
				.verifyComplete();
		verify(personRepository, times(1)).findAll();
	}

	@Test
	void testFindAdd(){
		PersonEntity person1 = new PersonEntity(1,"Angel",30);

		Mockito.when(personRepository.save(person1)).thenReturn(Mono.just(person1));
		StepVerifier.create(personService.create(person1))
				.expectNext(person1)
				.verifyComplete();
		verify(personRepository, times(1)).save(person1);


//		WebTestClient.post()
//				.uri("/api/employees")
//				.contentType(MediaType.APPLICATION_JSON)
//				.bodyValue(newEmployee)
//				.exchange()
//				.expectStatus().isCreated()
//				.expectBody(EmployeeDto.class)
//				.isEqualTo(newEmployee);
	}
}
