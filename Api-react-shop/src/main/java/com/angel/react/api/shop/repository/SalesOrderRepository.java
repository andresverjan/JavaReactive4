package com.angel.react.api.shop.repository;

import com.angel.react.api.shop.model.ProductTopFiveEntity;
import com.angel.react.api.shop.model.SalesOrderEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Repository
public interface SalesOrderRepository extends R2dbcRepository<SalesOrderEntity, Long> {
    Mono<SalesOrderEntity> findByReference(String reference);
    Flux<SalesOrderEntity> findByDateBetween(Date dateInit, Date dateEnd);
    Mono<Void> deleteByReference(String reference);
    @Query("SELECT idproduct, nameproduct, SUM(quantity) AS quantity, sum(total) as totalsale FROM sales_order_detail GROUP BY idproduct, nameproduct order by sum(quantity) desc limit 5")
    Flux<ProductTopFiveEntity> findStockTopFive();
}
