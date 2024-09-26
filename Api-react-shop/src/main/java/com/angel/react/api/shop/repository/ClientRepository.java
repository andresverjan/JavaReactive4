package com.angel.react.api.shop.repository;

import com.angel.react.api.shop.model.ClientEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends R2dbcRepository<ClientEntity, Long> {
}
