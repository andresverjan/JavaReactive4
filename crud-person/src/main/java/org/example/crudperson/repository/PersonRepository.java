package org.example.crudperson.repository;

import org.example.crudperson.model.Person;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.io.Serial;

@Repository
public interface PersonRepository extends R2dbcRepository<Person, Long> {


}
