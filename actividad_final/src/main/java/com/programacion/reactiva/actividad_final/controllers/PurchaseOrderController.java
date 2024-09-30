package com.programacion.reactiva.actividad_final.controllers;

import com.programacion.reactiva.actividad_final.model.PurchaseOrder;
import com.programacion.reactiva.actividad_final.model.PurchaseOrderProduct;
import com.programacion.reactiva.actividad_final.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @PostMapping
    public Mono<PurchaseOrder> createOrder(@RequestBody List<PurchaseOrderProduct> products) {
        return purchaseOrderService.createPurchaseOrder(products);
    }


    @GetMapping
    public Flux<PurchaseOrder> listOrders() {
        return purchaseOrderService.listPurchaseOrders();
    }

    @PutMapping("/{orderId}")
    public Mono<PurchaseOrder> updateOrder(@PathVariable Long orderId, @RequestBody List<PurchaseOrderProduct> products) {
        return purchaseOrderService.updatePurchaseOrder(orderId, products);
    }

    @DeleteMapping("/{orderId}")
    public Mono<Void> cancelOrder(@PathVariable Long orderId) {
        return purchaseOrderService.cancelPurchaseOrder(orderId);
    }

}

