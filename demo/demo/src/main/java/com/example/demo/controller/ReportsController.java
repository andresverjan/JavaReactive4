package com.example.demo.controller;

import com.example.demo.models.*;
import com.example.demo.service.OrdersService;
import com.example.demo.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reports")
public class ReportsController {
    @Autowired
    private OrdersService orderService;
    @Autowired
    private ShoppingService shoppingService;
    @GetMapping("/order")
    public Mono<OrderReport> getReport(@RequestBody Dates dates) {
        return orderService.getReport(dates);
    }
    @GetMapping("/shopping")
    public Mono <ShoppingReport> getReportShopping(@RequestBody Dates dates){
        return shoppingService.getReport(dates);
    }
}
