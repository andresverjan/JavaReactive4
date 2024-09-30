package com.angel.react.api.shop.repository;

import com.angel.react.api.shop.model.CartEntity;
import com.angel.react.api.shop.model.CartSummaryEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CartRepository extends R2dbcRepository<CartEntity, Long> {
    Flux<CartEntity> findByIdClient(Long idCliente);
    Mono<Void> deleteByIdClient(Long idClient);
    @Query("SELECT idclient, nameclient, sum(quantity) as totalproducts, sum(totalpriceproducts) as totalprice, \n" +
            "sum(totaldiscount) as totaldiscount, sum(totaliva) as totaliva, delivery FROM cart\n" +
            "where idclient = :idClient group by idclient,nameclient,delivery")
    Mono<CartSummaryEntity> findSummaryByClient(Long idClient);
}
