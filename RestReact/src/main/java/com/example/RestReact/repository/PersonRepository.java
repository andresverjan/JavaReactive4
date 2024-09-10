package com.example.RestReact.repository;

import com.example.RestReact.model.PersonEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<PersonEntity, Long> {
}
