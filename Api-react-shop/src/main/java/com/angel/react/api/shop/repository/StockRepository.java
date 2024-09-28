package com.angel.react.api.shop.repository;

import com.angel.react.api.shop.model.StockEntity;
import com.angel.react.api.shop.model.StockSummary;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface StockRepository extends R2dbcRepository<StockEntity, Long> {
    @Query("SELECT idproduct, SUM(quantity) AS stock FROM stock WHERE idproduct = :idProduct GROUP BY idproduct")
    Mono<StockSummary> findStockByIdProduct(Long idProduct);
}
