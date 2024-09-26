package com.angel.react.api.shop.repository;

import com.angel.react.api.shop.model.PersonEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends R2dbcRepository<PersonEntity, Long> {
}
