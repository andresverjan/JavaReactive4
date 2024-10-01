package com.javacourse.shoppingcart.controller;

import com.javacourse.shoppingcart.model.ProductCar;
import com.javacourse.shoppingcart.model.ShoppingCar;
import com.javacourse.shoppingcart.service.ProductCarService;
import com.javacourse.shoppingcart.service.ShoppingCarService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/shopping")
@AllArgsConstructor
public class ShoppingCarController {

    @Autowired
    private ShoppingCarService  shoppingCarService;

    @Autowired
    private ProductCarService productCarService;

    @PostMapping
    Mono<ShoppingCar> create (@RequestBody ShoppingCar shoppingCar){
       return shoppingCarService.create(shoppingCar);
    }

    @PostMapping("/products")
    Mono<ProductCar> create(@RequestBody ProductCar productCar){
       return productCarService.create(productCar);
    }

    @PutMapping
    Mono<String>update(@RequestBody ProductCar productCar){
        return productCarService.update(productCar);
    }

    @DeleteMapping
    Mono<Void>delete(@RequestBody ProductCar productCar){
        return productCarService.delete(productCar);
    }

    @DeleteMapping("/all")
    Mono<Void> deleteAll(@RequestBody ShoppingCar car){
        return productCarService.deleteAllByShoppingCar(car);
    }

    @GetMapping("/{id}")
    Mono<Double> sum(@RequestBody ShoppingCar car){
        return productCarService.sumTotal(car);
    }

    @GetMapping
    Flux<ProductCar> getProduct(@RequestBody ShoppingCar car){
        return productCarService.getProductByCar(car);
    }


}
