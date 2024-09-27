package org.api.repository;

import org.api.model.SalesOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface SalesRepository extends ReactiveCrudRepository<SalesOrder, Long> {
    Flux<SalesOrder> findAllByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);


}
