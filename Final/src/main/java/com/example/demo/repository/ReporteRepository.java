package com.example.demo.repository;

import com.example.demo.model.OrdenCompra;
import com.example.demo.model.OrdenProducto;
import com.example.demo.model.OrdenVenta;
import com.example.demo.model.Producto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface ReporteRepository {

    Flux<OrdenCompra> findComprasByFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    Flux<OrdenVenta> findVentasByFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    Flux<OrdenProducto> findAllByFechaOrdenBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}