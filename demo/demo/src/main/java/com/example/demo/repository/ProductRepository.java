package com.example.demo.repository;
import com.example.demo.models.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
    Mono<Product> findByName(String name);
}
