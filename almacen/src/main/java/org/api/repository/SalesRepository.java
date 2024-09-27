package org.api.repository;

import org.api.model.SalesOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SalesRepository extends ReactiveCrudRepository<SalesOrder, Long> {

}
