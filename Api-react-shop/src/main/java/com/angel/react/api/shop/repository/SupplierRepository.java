package com.angel.react.api.shop.repository;

import com.angel.react.api.shop.model.SupplierEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends R2dbcRepository<SupplierEntity, Long> {
}
