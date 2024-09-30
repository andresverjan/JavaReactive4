package com.programacion.reactiva.actividad_final.repository;

import com.programacion.reactiva.actividad_final.model.Order;
import com.programacion.reactiva.actividad_final.model.dto.ProductSalesDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {
    Flux<Order> findAllByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT p.name, SUM(op.quantity) as totalQuantity " +
            "FROM public.order_product op " +
            "JOIN public.product p ON op.product_id = p.id " +
            "JOIN public.order o ON op.order_id = o.id " +
            "WHERE o.created_at BETWEEN :startDate AND :endDate " +
            "GROUP BY p.name " +
            "ORDER BY totalQuantity DESC " +
            "LIMIT 5")
    Flux<ProductSalesDTO> findTop5ProductsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
}
