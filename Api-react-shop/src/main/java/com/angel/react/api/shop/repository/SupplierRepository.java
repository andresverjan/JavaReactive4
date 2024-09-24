package com.angel.react.api.shop.repository;

import com.angel.react.api.shop.model.SupplierEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends ReactiveCrudRepository<SupplierEntity, Long> {
}
