package com.example.demo.repository;

import com.example.demo.models.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<Person, Long>{
}
