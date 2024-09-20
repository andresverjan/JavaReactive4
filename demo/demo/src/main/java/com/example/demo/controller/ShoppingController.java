package com.example.demo.controller;

import com.example.demo.models.SaveResponse;
import com.example.demo.models.Shopping;
import com.example.demo.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/shopping")
public class ShoppingController {
    @Autowired
    private ShoppingService shoppingService;

    @PostMapping
    public Mono<SaveResponse> createShopping(@RequestBody Shopping shopping) {
        return shoppingService.createShopping(shopping);
    }
    // listado
    @GetMapping
    public Flux<Shopping> getAllShopping() {
        return shoppingService.getAllShopping();
    }

    //edicion
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Shopping>> updateShopping(@PathVariable Long id, @RequestBody Shopping shopping) {
        return shoppingService.updateShopping(id, shopping)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    //Eliminado
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteShopping(@PathVariable Long id) {
        return shoppingService.deleteShopping(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
