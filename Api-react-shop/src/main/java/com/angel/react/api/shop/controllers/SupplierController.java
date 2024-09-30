package com.angel.react.api.shop.controllers;

import com.angel.react.api.shop.model.PersonEntity;
import com.angel.react.api.shop.model.SupplierEntity;
import com.angel.react.api.shop.service.PersonService;
import com.angel.react.api.shop.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/supplier")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/retrieve")
    public Flux<SupplierEntity> finAll(){
        return this.supplierService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<SupplierEntity> finById(@PathVariable Long id){
        return this.supplierService.getById(id);
    }

    @PostMapping()
    public Mono<SupplierEntity> create(@RequestBody SupplierEntity supplier){
        return this.supplierService.create(supplier);
    }

    @PutMapping()
    public Mono<SupplierEntity> update(@RequestBody SupplierEntity supplier){
        return this.supplierService.update(supplier);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteById(@PathVariable Long id){
        return this.supplierService.deleteById(id);
    }
}
