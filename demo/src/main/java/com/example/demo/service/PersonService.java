package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class PersonService {
    private final PersonRepository personRepository;
    /*
    @Autowired
    private DatabaseClient databaseClient;
    private PersonRepository personRepository;
    public Mono<Void> testConnection() {
        return databaseClient.sql("SELECT 1")
                .fetch()
                .rowsUpdated()
                .then();
    }
    public Mono<Void> insertDataBd(User newUser) {
        return databaseClient.sql("INSERT INTO reactive.users (id, name, lastname, description) VALUES (:id, :name, :lastname, :description)")
                .bind("id", newUser.getId())
                .bind("name", newUser.getName())
                .bind("lastname",newUser.getLastname())
                .bind("description", newUser.getDescription())
                .fetch()
                .rowsUpdated()
                .then();
    }
*/
    public Mono<Person> getPersonById(Long id){
        if (id==null){
            return Mono.empty();
        }

        return personRepository.findById(id)
                .doOnNext(person -> System.out.println("Data getPersonById:  "+person))
                //.then(Mono.just("person search"))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Person not found with ID: " + id)))
                .onErrorResume(e -> {
                    System.out.println("Error during search: " + e.getMessage());
                    //return Mono.just("Error during search: " + e.getMessage());
                    return Mono.empty();
                });
                //.onErrorReturn("Person not found with ID: " + id);

    }
    public Flux<Person> getPersons(){
        return personRepository.findAll()
                .doOnNext(person -> System.out.println("Data Person:  "+person));
    }
    public Mono<Person> createPerson(Person person){
        return personRepository.save(person);
    }
    public Mono<String> updatePerson(Person person){
        if (person.getId()!=null){

            return personRepository.save(person)
                    .doOnNext(person2 -> {
                        System.out.println("Data person update: "+person2);
                    })
                    .then(Mono.just("update person data"))
                    /*.onErrorResume(e->{
                        String error="Error: "+e;
                        return Mono.just(error);
                        //return null;

                    })*/
                    .onErrorReturn("Error general update");

        }else {
            return Mono.just("Person not exist");
        }
        //return personRepository.save(person);
        //return null;
    }

    public Mono<String> deletePersonById(Long id){
        if (id == null){
            return Mono.error(new IllegalArgumentException("ID no puede ser nullo"));
        }else{
            System.out.println("a eliminar....");
            return personRepository.findById(id)
                    .flatMap(person -> personRepository.deleteById(id)
                            .doOnNext(deleted -> System.out.println("Person Delete: " + person))
                            .then(Mono.just("Person delete: .."+id))
                            .onErrorResume(e -> {
                                System.out.println("Error during deletion: " + e.getMessage());
                                return Mono.just("Error during deletion: " + e.getMessage());
                            }))
                    .switchIfEmpty(Mono.error(new IllegalArgumentException("Person not found with ID: " + id)))
                    .onErrorReturn(IllegalArgumentException.class, "Error: Person not found or invalid ID");
        }

    }
}
