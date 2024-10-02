package com.curso.java.reactor.repository;

import com.curso.java.reactor.models.PurchaseOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface PurchaseOrderRepository extends ReactiveCrudRepository<PurchaseOrder, Long> {
    Flux<PurchaseOrder> findAllByUpdatedAtBetween(LocalDateTime initDate, LocalDateTime endDate);

}

