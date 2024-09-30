package com.programacion.reactiva.actividad_final.repository;

import com.programacion.reactiva.actividad_final.model.OrderProduct;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OrderProductRepository extends ReactiveCrudRepository<OrderProduct, Long> {
    public Mono<OrderProduct> findByOrderIdAndProductId(long orderId, long productId);
    public Mono<Void> deleteAllByOrderId(long orderId);
    public Flux<OrderProduct> findByOrderId(long orderId);
}
