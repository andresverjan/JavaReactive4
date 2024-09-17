package com.excercise3.test_r2dbc.repositories;

import com.excercise3.test_r2dbc.entities.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<Person, Long> {
}