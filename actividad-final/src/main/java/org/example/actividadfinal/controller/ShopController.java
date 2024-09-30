package org.example.actividadfinal.controller;

import lombok.AllArgsConstructor;
import org.example.actividadfinal.model.ResponseData;
import org.example.actividadfinal.model.Shop;
import org.example.actividadfinal.service.ShopService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/shop")
@AllArgsConstructor
public class ShopController {

    private ShopService shopService;

    @PostMapping(value = "/add")
    public Mono<ResponseData> addProduct(@RequestBody Shop shop) {
        return shopService.addProduct(shop);
    }

    @DeleteMapping(value = "/delete/{id}")
    public Mono<ResponseData> deleteProductOfShoppingCart(@PathVariable String id) {
        return shopService.deleteProductByShoppingCart(Long.parseLong(id));
    }

    @PostMapping(value = "/update-amount")
    public Mono<ResponseData> updateAmmount(@RequestBody Map<String, Long> mapRequest) {
        return shopService.incrementAmount(mapRequest);
    }

    @GetMapping(value = "/list-cart/{idShop}")
    public Mono<ResponseData> getListCart(@PathVariable String idShop) {
        return shopService.getListOfShoppingCart(Long.parseLong(idShop));
    }

    @DeleteMapping(value = "/empty-cart/{id}")
    public Mono<ResponseData> emptyCart(@PathVariable String id) {
        return shopService.emptyCart(Long.parseLong(id));
    }

    @GetMapping(value = "/total-shop/{id}")
    public Mono<ResponseData> totalShop(@PathVariable String id) {
        return shopService.totalShop(Long.parseLong(id));
    }
}
