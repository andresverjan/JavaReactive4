package com.example.trabajoFinal.repository;


import com.example.trabajoFinal.model.OrdenesCompra;
import com.example.trabajoFinal.model.OrdenesVentas;
import com.example.trabajoFinal.model.TopVentas;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface AdminReportRepository extends ReactiveCrudRepository<OrdenesCompra, Integer> {

    @Query("SELECT * FROM public.ordencompra " +
            "WHERE created_at " +
            "BETWEEN :startDate AND :endDate")
    Flux<OrdenesCompra> reportOrdenesCompra(LocalDateTime startDate,
                                            LocalDateTime endDate);

    @Query("SELECT * FROM public.ordenventa " +
            "WHERE created_at " +
            "BETWEEN :startDate AND :endDate")
    Flux<OrdenesVentas> reportOrdenesVentas(LocalDateTime startDate,
                                            LocalDateTime endDate);

    @Query("SELECT p.name AS name, SUM(ov.cantidad) AS cantidad " +
            "FROM public.ordenventa ov " +
            "JOIN public.productos p ON ov.product_id  = p.id " +
            "JOIN public.persona p2 ON ov.persona_id  = p2.id " +
            "WHERE ov.created_at " +
            "BETWEEN :startDate AND :endDate " +
            "GROUP BY p.name " +
            "ORDER BY SUM(ov.cantidad) DESC " +
            "LIMIT 5")
    Flux<TopVentas> reportTop5ProductsBetweenDates(LocalDateTime startDate,
                                                   LocalDateTime endDate);

}
