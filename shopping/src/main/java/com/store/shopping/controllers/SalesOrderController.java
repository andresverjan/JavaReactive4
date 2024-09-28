package com.store.shopping.controllers;

import com.store.shopping.DTO.TopFiveDTO;
import com.store.shopping.entities.Product;
import com.store.shopping.entities.Sales;
import com.store.shopping.useCase.SalesService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/sell")
public class SalesOrderController {
    private final SalesService salesService;

    public SalesOrderController(SalesService salesService) {
        this.salesService = salesService;
    }
    @GetMapping("/salecart/{id}")
    public Flux<Sales> saleCartProducts(@PathVariable String id) {
        return salesService.generateSale(id);
    }
    @GetMapping("/report/sale")
    public Flux<Sales> saleCartProducts(@RequestParam(name = "init") String init,
                                        @RequestParam(name = "end") String end) {
        return salesService.generateSalesReport(init,end);
    }
    @GetMapping("/report/top")
    public Flux<TopFiveDTO> topFive(@RequestParam(name = "init") String init,
                                             @RequestParam(name = "end") String end) {
        return salesService.topFive(init,end);
    }
}
