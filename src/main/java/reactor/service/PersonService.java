package reactor.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.model.PersonEntity;
import reactor.repository.PersonRepository;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public Flux<PersonEntity> getPersons(){
        return personRepository.findAll();
    }

    public Mono<PersonEntity> getPersonById(Integer id){
        if (id == null) {
            return Mono.empty();
        }
        return personRepository.findById(id);
    }

    public Mono<PersonEntity> save(PersonEntity person){
        /*if (person.getAge() == null || person.getAge() < 0) {
            return Mono.error(new IllegalArgumentException("La edad no es v\u00E1lida. Debe ser un n\u00FAmerica."));
        }*/
        return personRepository.save(person);
    }
    public Mono<String> update(PersonEntity person) {
        if (person.getId() != null) {
            return personRepository.findById(person.getId()).flatMap(existPerson -> {
                existPerson.setName(person.getName() != null ? person.getName() : existPerson.getName());
                existPerson.setBirthdate(person.getBirthdate() != null ? person.getBirthdate() : existPerson.getBirthdate());
                existPerson.setAge(person.getAge() != null ? person.getAge() : existPerson.getAge());
                existPerson.setGender(person.getGender() != null ? person.getGender() : existPerson.getGender());
                existPerson.setBooldType(person.getBooldType() != null ? person.getBooldType() : existPerson.getBooldType());
                return personRepository.save(existPerson);
            })
            .then(Mono.just("Usuario actualizado"));
        } else {
            return Mono.error(new RuntimeException("La persona no existe."));
        }
    }

    public Mono<Void> deletePersonById(Integer id){
        if (id == null) {
            return Mono.error(new IllegalArgumentException("Debes enviar un id"));
        }
        return personRepository.deleteById(id);
    }
}
