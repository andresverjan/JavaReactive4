package com.curso.java.reactor.repository;

import com.curso.java.reactor.models.SaleOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface SaleOrderRepository extends ReactiveCrudRepository<SaleOrder, Long> {
    Flux<SaleOrder> findAllByUpdatedAtBetween(LocalDateTime initDate, LocalDateTime endDate);

}
