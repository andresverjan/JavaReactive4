package org.api.controllers;

import org.api.model.PurchaseDto;
import org.api.model.PurchaseOrder;
import org.api.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/compras")
public class PurchaseController {

     @Autowired
    private PurchaseService ordenPurchaseService;


    @PostMapping
    public Mono<PurchaseOrder> crearOrdenCompra(@RequestBody PurchaseOrder ordenCompra) {
        return ordenPurchaseService.crearOrdenCompra(ordenCompra);
    }

    @GetMapping
    public Flux<PurchaseOrder> listarOrdenes() {
        return ordenPurchaseService.listarOrdenes();
    }

    @GetMapping("/reportes")
    public Flux<PurchaseOrder> obtenerReportesCompras(@RequestParam LocalDateTime startDate,
                                                    @RequestParam LocalDateTime endDate) {
        return ordenPurchaseService.obtenerReportesCompras(startDate, endDate);
    }
}

