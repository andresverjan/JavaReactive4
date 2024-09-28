package com.excercise3.test_r2dbc.drivenAdapters.postgres.iPostgres;

import com.excercise3.test_r2dbc.entities.Clases;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClasesRepository extends R2dbcRepository<Clases, Long> {
}
