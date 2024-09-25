package com.example.shopping_cart.controllers;

import com.example.shopping_cart.model.PurchaseItem;
import com.example.shopping_cart.model.PurchaseOrder;
import com.example.shopping_cart.service.PurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/inventaryPurchase")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping("/addPurchaseOrder/")
    Mono<PurchaseOrder> createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder){return purchaseService.createPurchaseOrder(purchaseOrder);}


    @PostMapping("/addPurchaseItem/")
    Flux<PurchaseItem> createPurchaseItem(@RequestBody List<PurchaseItem> purchaseItems){return purchaseService.createPurchaseItem(purchaseItems);}

    @GetMapping("/listPurchaseOrders/")
    Flux<PurchaseOrder> listPurchaseOrders(){return purchaseService.getPurchaseOrders();}

    @PutMapping("/completePurchaseOrder/{id}")
    Mono<PurchaseOrder> completePurchaseOrder(@PathVariable Integer id){return purchaseService.completePurchaseOrder(id);}

    @PutMapping("/canceledPurchaseOrder/{id}")
    Mono<PurchaseOrder> canceledPurchaseOrder(@PathVariable Integer id){return purchaseService.canceledPurchaseOrder(id);}

    @PutMapping("/updatePurchaseOrder/")
    Mono<PurchaseOrder> updatePurchaseOrder(@RequestBody PurchaseOrder purchaseOrder){return purchaseService.updatePurchaseOrder(purchaseOrder);}

    @GetMapping("/report")
    public Flux<PurchaseOrder> reportPurchaseOrder(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return purchaseService.reportPurchaseOrder(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
    }
}
