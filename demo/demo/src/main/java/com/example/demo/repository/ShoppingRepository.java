package com.example.demo.repository;


import com.example.demo.models.Orders;
import com.example.demo.models.Shopping;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface ShoppingRepository extends ReactiveCrudRepository<Shopping, Long> {
    Flux<Shopping> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
