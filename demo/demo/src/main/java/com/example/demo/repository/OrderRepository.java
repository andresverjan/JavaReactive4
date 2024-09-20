package com.example.demo.repository;

import com.example.demo.models.Orders;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Orders, Long> {
    Flux<Orders> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);


}
