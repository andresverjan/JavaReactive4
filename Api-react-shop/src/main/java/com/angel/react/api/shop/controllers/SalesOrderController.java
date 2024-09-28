package com.angel.react.api.shop.controllers;

import com.angel.react.api.shop.model.SalesOrderEntity;
import com.angel.react.api.shop.model.SalesOrderFull;
import com.angel.react.api.shop.service.SalesOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@Slf4j
@RestController
@RequestMapping("/api/v1/sales-order")
public class SalesOrderController {

    private final SalesOrderService salesOrderService;

    public SalesOrderController(SalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @GetMapping("/retrieve")
    public Flux<SalesOrderEntity> finAll(){
        return this.salesOrderService.getAll();
    }

    @GetMapping("/details/reference/{reference}")
    public Mono<SalesOrderFull> finByReference(@PathVariable String reference){
        return this.salesOrderService.getDetailsByReference(reference);
    }

    @GetMapping("/report")
    public Flux<SalesOrderEntity> getReport(@RequestParam String dateInit, @RequestParam String dateEnd) throws ParseException {
        return this.salesOrderService.getByDates(dateInit, dateEnd);
    }

    @PostMapping()
    public Mono<SalesOrderFull> create(@RequestBody SalesOrderFull salesOrderFull){
        return this.salesOrderService.create(salesOrderFull);
    }

    @DeleteMapping("/{reference}")
    public Mono<ResponseEntity<String>> deleteByReference(@PathVariable String reference){
        return salesOrderService.deleteByReference(reference);
    }
}
