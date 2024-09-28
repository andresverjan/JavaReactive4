package com.angel.react.api.shop.controllers;

import com.angel.react.api.shop.model.PurchaseOrderDetailEntity;
import com.angel.react.api.shop.model.PurchaseOrderEntity;
import com.angel.react.api.shop.model.PurchaseOrderFull;
import com.angel.react.api.shop.service.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/purchase-order")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping("/retrieve")
    public Flux<PurchaseOrderEntity> finAll(){
        return this.purchaseOrderService.getAll();
    }

    @GetMapping("/details/reference/{reference}")
    public Mono<PurchaseOrderFull> finByReference(@PathVariable String reference){
        return this.purchaseOrderService.getDetailsByReference(reference);
    }

    @GetMapping("/report")
    public Flux<PurchaseOrderEntity> getReport(@RequestParam String dateInit, @RequestParam String dateEnd) throws ParseException {
        return this.purchaseOrderService.getByDates(dateInit, dateEnd);
    }

    @PostMapping()
    public Mono<PurchaseOrderFull> create(@RequestBody PurchaseOrderFull purchaseOrder){
        return this.purchaseOrderService.create(purchaseOrder);
    }

    @PutMapping()
    public Mono<PurchaseOrderEntity> update(@RequestBody PurchaseOrderEntity purchaseOrderEntity){
        return this.purchaseOrderService.update(purchaseOrderEntity);
    }

    @DeleteMapping("/{reference}")
    public Mono<ResponseEntity<String>> deleteByReference(@PathVariable String reference){
        return purchaseOrderService.deleteByReference(reference);
    }
}
