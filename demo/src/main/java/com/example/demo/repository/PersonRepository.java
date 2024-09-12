package com.example.demo.repository;

import com.example.demo.model.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonRepository  extends ReactiveCrudRepository<Person, Long> {
}
