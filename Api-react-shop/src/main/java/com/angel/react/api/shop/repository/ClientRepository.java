package com.angel.react.api.shop.repository;

import com.angel.react.api.shop.model.ClientEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends ReactiveCrudRepository<ClientEntity, Long> {
}
