package org.example.actividadfinal.repository;

import org.example.actividadfinal.model.SalesOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderRepository extends ReactiveCrudRepository<SalesOrder, Long> {
}
