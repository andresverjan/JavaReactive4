package com.curso.java.reactor.repository;

import com.curso.java.reactor.models.Cart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends ReactiveCrudRepository<Cart, Long> {
}
