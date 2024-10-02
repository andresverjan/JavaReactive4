package com.curso.java.reactor.controllers;

import lombok.AllArgsConstructor;
import com.curso.java.reactor.models.dto.SaleOrderDTO;
import com.curso.java.reactor.models.dto.SaleOrderRequestDTO;
import com.curso.java.reactor.models.dto.SaleDTO;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.curso.java.reactor.services.SaleOrderService;

@RestController
@RequestMapping("/sale")
@AllArgsConstructor
public class SaleOrderController {
    private final SaleOrderService saleOrderService;

    @PostMapping
    public Mono<SaleDTO> createDirectSalesOrder(@RequestBody SaleOrderRequestDTO saleOrderRequest) {
        return saleOrderService.createDirectSalesOrder(saleOrderRequest.getProducts(), saleOrderRequest.getShipment());
    }

    @GetMapping
    public Flux<SaleOrderDTO> listSaleOrders() {
        return saleOrderService.listSaleOrders();
    }

    @GetMapping("/{saleOrderId}")
    public Mono<SaleOrderDTO> getSaleOrderById(@PathVariable Long saleOrderId) {
        return saleOrderService.getSaleOrderById(saleOrderId);
    }

    @GetMapping("/product/{productId}")
    public Flux<SaleOrderDTO> getSaleOrderByProduct(@PathVariable Long productId) {
        return saleOrderService.getSaleOrderByProduct(productId);
    }

    @PutMapping("/{saleOrderId}")
    public Mono<SaleOrderDTO> setStateSaleOrder(@PathVariable Long saleOrderId, @RequestParam String state) {
        return saleOrderService.setStateSaleOrder(saleOrderId, state);
    }

    @GetMapping("/report/date")
    public Flux<SaleOrderDTO> listSaleOrdersBetweenDates(@RequestParam String initDate, @RequestParam String endDate) {
        return saleOrderService.listSaleOrdersBetweenDates(initDate, endDate);
    }

    @GetMapping("/report/top5")
    public Flux<SaleOrderDTO> getTopFiveSalesBetweenDates(@RequestParam String initDate, @RequestParam String endDate) {
        return saleOrderService.getTopFiveSalesBetweenDates(initDate, endDate);
    }
}
