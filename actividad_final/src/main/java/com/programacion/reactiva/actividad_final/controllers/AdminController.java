package com.programacion.reactiva.actividad_final.controllers;

import com.programacion.reactiva.actividad_final.model.Order;
import com.programacion.reactiva.actividad_final.model.PurchaseOrder;
import com.programacion.reactiva.actividad_final.model.dto.ProductSalesDTO;
import com.programacion.reactiva.actividad_final.repository.OrderRepository;
import com.programacion.reactiva.actividad_final.repository.PurchaseOrderRepository;
import com.programacion.reactiva.actividad_final.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/report/purchases")
    public Flux<PurchaseOrder> getPurchasesReport(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return adminService.getPurchasesReport(startDate, endDate);
    }

    @GetMapping("/report/sales")
    public Flux<Order> getSalesReport(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return adminService.getSalesReport(startDate, endDate);
    }

    @GetMapping("/report/products")
    public Flux<ProductSalesDTO> getTop5ProductsReport(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return adminService.getTop5ProductsReport(startDate, endDate);
    }
}
