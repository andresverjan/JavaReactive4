package com.example.demo.service;


import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class PersonServiceTest {


    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

   // @BeforeEachvoid
    //setUp()
    //{    MockitoAnnotations.openMocks(this);}

    @Test
    void contextLoads(){

    }

    @Test
    void testFindAll(){
        Person person1 = new Person(1,"John",20);
        Person person2 = new Person(1,"John",20);
        Mockito.when(personRepository.findAll()).thenReturn(Flux.just(person1,person2));

        StepVerifier.create(personService.getPersons())
                .expectNext(person1)
                .expectNext(person2)
                .verifyComplete();
        verify(personRepository,times(1)).findAll();
    }

}
