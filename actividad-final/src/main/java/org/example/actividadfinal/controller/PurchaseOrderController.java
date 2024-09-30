package org.example.actividadfinal.controller;

import lombok.AllArgsConstructor;
import org.example.actividadfinal.model.PurchaseOrder;
import org.example.actividadfinal.model.ResponseData;
import org.example.actividadfinal.service.PurchaseOrderService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/purchase-order")
@AllArgsConstructor
public class PurchaseOrderController {

    private PurchaseOrderService purchaseOrderService;

    @PostMapping
    public Mono<ResponseData> createOrder(@RequestBody PurchaseOrder purchaseOrder) {
        return purchaseOrderService.createOrder(purchaseOrder);
    }

    @PostMapping("/{id}")
    public Mono<ResponseData> updateOrder(@PathVariable String id) {
        return null;
    }

    @PostMapping("/update-state")
    public Mono<ResponseData> updateState(@RequestBody Map<String, String> mapRequest) {
        return null;
    }

    @GetMapping
    public Mono<ResponseData> getOrders(){
        return null;
    }

    @GetMapping("/order-products/{id}")
    public Mono<ResponseData> getProductsByOrder(@PathVariable String id) {
        return null;
    }
}
