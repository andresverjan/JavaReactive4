package org.example.actividadfinal.controller;

import lombok.AllArgsConstructor;
import org.example.actividadfinal.model.PurchaseOrder;
import org.example.actividadfinal.model.ResponseData;
import org.example.actividadfinal.model.Shop;
import org.example.actividadfinal.service.PurchaseOrderService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/purchase-order")
@AllArgsConstructor
public class PurchaseOrderController {

    private PurchaseOrderService purchaseOrderService;
    @GetMapping
    public Mono<ResponseData> getOrders() {
        return purchaseOrderService.getOrders();
    }

    @PostMapping
    public Mono<ResponseData> createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        return purchaseOrderService.createPurchaseOrder(purchaseOrder, false);
    }

    @PutMapping(value = "/{id}")
    public Mono<ResponseData> updatePurchaseOrder(@PathVariable String id) {
        return purchaseOrderService.updatePurchaseOrder(Long.parseLong(id));
    }

    @PostMapping(value = "/cancel/{id}")
    public Mono<ResponseData> cancelPurchaseOrder(@PathVariable String id) {
        return purchaseOrderService.cancelPurchaseOrder(Long.parseLong(id));
    }
}
