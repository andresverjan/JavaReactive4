package com.programacion.reactiva.actividad_final.repository;

import com.programacion.reactiva.actividad_final.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    Mono<User> findByEmailAndPassword(String email, String password);
}
