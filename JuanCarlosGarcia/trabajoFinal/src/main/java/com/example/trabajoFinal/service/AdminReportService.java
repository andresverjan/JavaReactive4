package com.example.trabajoFinal.service;

import com.example.trabajoFinal.model.OrdenesCompra;
import com.example.trabajoFinal.model.OrdenesVentas;
import com.example.trabajoFinal.model.TopVentas;
import com.example.trabajoFinal.repository.AdminReportRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Service
public class AdminReportService {

    private final AdminReportRepository adminReportRepository;
    public AdminReportService(AdminReportRepository adminReportRepository) {
        this.adminReportRepository = adminReportRepository;
    }

    public Flux<OrdenesCompra> reportOrdenesCompra(LocalDateTime startDate, LocalDateTime endDate){
        return adminReportRepository.reportOrdenesCompra(startDate,endDate);
    }

    public Flux<OrdenesVentas> reportOrdenesVentas(LocalDateTime startDate, LocalDateTime endDate){
        return adminReportRepository.reportOrdenesVentas(startDate,endDate);
    }

    public Flux<TopVentas> reportTop5Products(LocalDateTime startDate, LocalDateTime endDate){
        return adminReportRepository.reportTop5ProductsBetweenDates(startDate,endDate);
    }
}
