package org.example.actividadfinal.service;

import lombok.AllArgsConstructor;
import org.example.actividadfinal.exceptions.OrderException;
import org.example.actividadfinal.model.ResponseData;
import org.example.actividadfinal.model.SalesOrder;
import org.example.actividadfinal.repository.ReportRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReportService {


    private final ReportRepository reportRepository;

    public Mono<ResponseData> getPurchaseOrderByDateRange(String startTime, String endTime) {
        return reportRepository.getPurchaseOrderByDateRange(parseLocalDateTime(startTime), parseLocalDateTime(endTime))
            .switchIfEmpty(Mono.defer(() -> Mono.error(new OrderException("No encontrado ordenes de compra"))))
            .collectList()
            .flatMap(p -> Mono.just(ResponseData.builder()
                    .data(p)
                    .build()));
    }


    public Mono<ResponseData> getSalesOrderByDateRange(String startTime, String endTime) {
        return reportRepository.getSalesOrderByDateRange(parseLocalDateTime(startTime), parseLocalDateTime(endTime))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new OrderException("No encontrado ordenes de venta"))))
                .collectList()
                .flatMap(p -> Mono.just(ResponseData.builder()
                        .data(p)
                        .build()));
    }

    public Mono<ResponseData> topFiveOfSales() {
        return reportRepository.topFiveOfProducts()
                .switchIfEmpty(Mono.defer(() -> Mono.error(new OrderException("No encontrado ordenes de venta"))))
                .collectList()
                .flatMap(p -> Mono.just(ResponseData.builder()
                        .data(p)
                        .build()));
    }

    public LocalDateTime parseLocalDateTime(String localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            return LocalDateTime.parse(localDateTime, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
        return LocalDateTime.now();
    }
}
