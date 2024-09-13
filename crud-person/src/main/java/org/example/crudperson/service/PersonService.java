package org.example.crudperson.service;

import lombok.AllArgsConstructor;
import org.example.crudperson.exceptions.NotFoundException;
import org.example.crudperson.model.Person;
import org.example.crudperson.repository.PersonRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;


    public Mono<List<Person>> getPerson() {
        return personRepository.findAll()
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException("No se encontro información de personas"))))
                .collectList();
    }

    public Mono<String> savePerson(Person person) {
        return personRepository.save(person)
                .thenReturn("Usuario creado exitosamente");
    }

    public Mono<Person> findById(Long id) {
        return personRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException("No se encontro persona con id: " + id))));
    }

    public Mono<String> deleteById(Long id) {
        return personRepository.findById(id)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException("No se encontro persona con id: " + id))))
            .flatMap(p -> personRepository.deleteById(p.getId()))
            .then()
            .thenReturn("Se elimino el usuario con id: " + id);
    }

    public Mono<String> updatePerson(Person person) {
        return personRepository.findById(person.getId())
            .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException("No se encontro persona con id: " + person.getId()))))
            .flatMap(p -> Mono.just(Person.builder()
                .id(p.getId())
                .gender(Objects.nonNull(person.getGender())? person.getGender(): p.getGender())
                .age(Objects.nonNull(person.getAge())? person.getAge(): p.getAge())
                .name(Objects.nonNull(person.getName())? person.getName(): p.getName())
                .dateOfBirth(Objects.nonNull(person.getDateOfBirth())? person.getDateOfBirth(): p.getDateOfBirth())
                .createdDate(p.getCreatedDate())
                .bloodType(Objects.nonNull(person.getBloodType()) ? person.getBloodType() : p.getBloodType())
                .build())
            ).flatMap(personRepository::save)
            .thenReturn("Usuario actualizado exitosamente!");
    }
}