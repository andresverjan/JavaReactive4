package valko.co.ejercicio5.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import valko.co.ejercicio5.exception.PersonNotFoundException;
import valko.co.ejercicio5.model.Person;
import valko.co.ejercicio5.repository.PersonRepository;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Flux<Person> listAllPersons() {
        return personRepository.findAll();
    }

    public Mono<Person> findPersonById(Integer id) {
        return personRepository.findById(id)
                .switchIfEmpty(Mono.error(new PersonNotFoundException("Person with ID " + id + " not found")));
    }

    public Mono<Person> savePerson(Person person) {
        return personRepository.save(person);
    }

    public Mono<Person> updatePerson(Integer id, Person person) {
        return personRepository.findById(id)
                .switchIfEmpty(Mono.error(new PersonNotFoundException("Person with ID " + id + " not found")))
                .flatMap(existingPerson -> {
                    existingPerson.setName(person.getName());
                    existingPerson.setAge(person.getAge());
                    existingPerson.setGender(person.getGender());
                    existingPerson.setDateOfBirth(person.getDateOfBirth());
                    existingPerson.setBloodType(person.getBloodType());
                    return personRepository.save(existingPerson);
                });
    }

    public Mono<Void> deletePersonById(Integer id) {
        return personRepository.findById(id)
                .switchIfEmpty(Mono.error(new PersonNotFoundException("Person with ID " + id + " not found")))
                .flatMap(personRepository::delete);
    }
}
