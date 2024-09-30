package com.angel.react.api.shop.service;

import com.angel.react.api.shop.model.PersonEntity;
import com.angel.react.api.shop.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Flux<PersonEntity> getAll(){
        return personRepository.findAll()
                .switchIfEmpty(Mono.fromRunnable(() -> log.info("No persons found")))
                .doOnComplete(() -> log.info("Find all persons success"))
                .doOnError(error -> log.error("Error finding all persons: {}", error.getMessage()));
    }

    public Mono<PersonEntity> getById(Long id){
        return personRepository.findById(id)
                .switchIfEmpty(Mono.fromRunnable(() -> log.info("Person not found, id: {}", id)))
                .doOnNext(p -> log.info("Person found, id: {}", id))
                .doOnError(error -> log.error("Error finding the person ID {}: {}", id, error.getMessage()));
    }

    public Mono<PersonEntity> create(PersonEntity person){
        return personRepository.save(person)
                .doOnNext(p -> log.info("Person created, id: {}", person.getId()))
                .doOnError(error -> log.error("Error creating the person NAME {}: {}", person.getName(), error.getMessage()));
    }

    public Mono<PersonEntity> update(PersonEntity person){
        return personRepository.save(person)
                .doOnNext(p -> log.info("Person update, id: {}", person.getId()))
                .doOnError(error -> log.error("Error updating the person ID {}: {}", person.getId(), error.getMessage()));
    }

    public Mono<ResponseEntity<String>> deleteById(Long id) {
        return personRepository.findById(id)
                .flatMap(item -> personRepository.deleteById(id)
                        .then(Mono.fromCallable(() -> {
                            log.info("Person [{}] deleted", item.getName());
                            return ResponseEntity.ok("deleted ok");
                        }))
                )
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("Person ID {} not found, error deleting", id);
                    return Mono.just(ResponseEntity.notFound().build());
                }))
                .doOnError(error -> log.error("Error deleting person ID {}: {}", id, error.getMessage()));
    }
}
