package org.example.actividadfinal.controller;

import lombok.AllArgsConstructor;
import org.example.actividadfinal.model.ResponseData;
import org.example.actividadfinal.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/report")
@AllArgsConstructor
public class ReportController {

    private ReportService reportService;

    @GetMapping("/purchase-order/{startTime}/{endTime}")
    public Mono<ResponseData> getPurchaseOrder(@PathVariable String startTime, @PathVariable String endTime) {
        return reportService.getPurchaseOrderByDateRange(startTime, endTime);
    }

    @GetMapping("/sales-order/{startTime}/{endTime}")
    public Mono<ResponseData> getSalesOrder(@PathVariable String startTime, @PathVariable String endTime) {
        return reportService.getSalesOrderByDateRange(startTime, endTime);
    }

    @GetMapping("/top-five")
    public Mono<ResponseData> topFiveOfSales() {
        return reportService.topFiveOfSales();
    }
}
