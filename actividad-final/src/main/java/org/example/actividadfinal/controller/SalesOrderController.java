package org.example.actividadfinal.controller;

import lombok.AllArgsConstructor;
import org.example.actividadfinal.model.SalesOrder;
import org.example.actividadfinal.model.ResponseData;
import org.example.actividadfinal.service.SalesOrderService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/sales-order")
@AllArgsConstructor
public class SalesOrderController {

    private SalesOrderService salesOrderService;

    @PostMapping
    public Mono<ResponseData> createOrder(@RequestBody SalesOrder salesOrder) {
        return salesOrderService.createOrEditOrder(salesOrder, false);
    }

    @PutMapping("/{id}")
    public Mono<ResponseData> updateOrder(@PathVariable String id) {
        return salesOrderService.updateOrder(Long.parseLong(id));
    }

    @PutMapping("/update-state")
    public Mono<ResponseData> updateState(@RequestBody Map<String, String> mapRequest) {
        return salesOrderService.updateState(mapRequest);
    }

    @GetMapping
    public Mono<ResponseData> getOrders(){
        return salesOrderService.getOrders();
    }

    @GetMapping("/order-products/{id}")
    public Mono<ResponseData> getProductsByOrder(@PathVariable String id) {
        return salesOrderService.getProductsByOrder(Long.parseLong(id));
    }
}
