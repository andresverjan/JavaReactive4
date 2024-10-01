package com.example.trabajoFinal.controller;

import com.example.trabajoFinal.model.DateRange;
import com.example.trabajoFinal.model.OrdenesCompra;
import com.example.trabajoFinal.model.OrdenesVentas;
import com.example.trabajoFinal.model.TopVentas;
import com.example.trabajoFinal.service.AdminReportService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalTime;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminReportService adminReportService;

    public AdminController(AdminReportService adminReportService) {
        this.adminReportService = adminReportService;
    }

    @PostMapping("/reporteOrdenesCompra")
    public Flux<OrdenesCompra> reportOrdenesCompra(@RequestBody DateRange dateRange) {
        return adminReportService.reportOrdenesCompra(dateRange.getStartDate().atStartOfDay(),
                dateRange.getEndDate().atTime(LocalTime.MAX));
    }

    @PostMapping("/reporteOrdenesVentas")
    public Flux<OrdenesVentas> reportOrdenesVentas(@RequestBody DateRange dateRange) {
        return adminReportService.reportOrdenesVentas(dateRange.getStartDate().atStartOfDay(),
                dateRange.getEndDate().atTime(LocalTime.MAX));
    }
    @PostMapping("/reporteTopVentas")
    public Flux<TopVentas> reportTopVentas(@RequestBody DateRange dateRange) {
        return adminReportService.reportTop5Products(dateRange.getStartDate().atStartOfDay(),
                dateRange.getEndDate().atTime(LocalTime.MAX));
    }
}
