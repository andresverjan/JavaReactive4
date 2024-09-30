package com.programacion.reactiva.actividad_final.service;

import com.programacion.reactiva.actividad_final.model.Order;
import com.programacion.reactiva.actividad_final.model.PurchaseOrder;
import com.programacion.reactiva.actividad_final.model.dto.ProductSalesDTO;
import com.programacion.reactiva.actividad_final.repository.OrderRepository;
import com.programacion.reactiva.actividad_final.repository.PurchaseOrderRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Service
public class AdminService {
    public AdminService(PurchaseOrderRepository purchaseOrderRepository, OrderRepository orderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.orderRepository = orderRepository;
    }

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final OrderRepository orderRepository;

    public Flux<PurchaseOrder> getPurchasesReport(LocalDate startDate, LocalDate endDate) {

        return purchaseOrderRepository.findAllByCreatedAtBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
    }

    public Flux<Order> getSalesReport(LocalDate startDate, LocalDate endDate) {

        return orderRepository.findAllByCreatedAtBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
    }

    public Flux<ProductSalesDTO> getTop5ProductsReport(LocalDate startDate,LocalDate endDate) {

        return orderRepository.findTop5ProductsBetweenDates(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
    }
}
