package com.example.shopping_cart.repository;

import com.example.shopping_cart.model.PurchaseOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface PurchaseOrderRepository extends ReactiveCrudRepository<PurchaseOrder, Integer> {
    Flux<PurchaseOrder> findAllByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
