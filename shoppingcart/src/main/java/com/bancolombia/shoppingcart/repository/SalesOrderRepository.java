package com.bancolombia.shoppingcart.repository;

import com.bancolombia.shoppingcart.entity.SalesOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderRepository extends ReactiveCrudRepository<SalesOrder, Long> {
}
