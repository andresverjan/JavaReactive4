package com.reactive.crud.repository;


import com.reactive.crud.models.Person;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends R2dbcRepository<Person, Long> {
}