package com.curso.java.reactor.controllers;

import com.curso.java.reactor.models.dto.PurchaseOrderDTO;
import com.curso.java.reactor.models.dto.PurchaseOrderRequestDTO;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.curso.java.reactor.services.PurchaseOrderService;

@RestController
@RequestMapping("/purchase-order")
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping
    public Flux<PurchaseOrderDTO> getPurchaseOrdersList() {
        return purchaseOrderService.listarOrdenesComra();
    }


    @PostMapping
    public Mono<PurchaseOrderDTO> createPurchaseOrder(@RequestBody PurchaseOrderRequestDTO purchaseOrderRequestDTO) {
        return purchaseOrderService.registrarOrdenCompra(purchaseOrderRequestDTO.getProducts(), purchaseOrderRequestDTO.getTotal());
    }

    @GetMapping("/report")
    public Flux<PurchaseOrderDTO> getPurchaseOrdersBetweenDates(@RequestParam String initDate, @RequestParam String endDate) {
        return purchaseOrderService.getPurchaseOrdersBetweenDates(initDate, endDate);
    }

}
