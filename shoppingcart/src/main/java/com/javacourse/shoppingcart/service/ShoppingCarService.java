package com.javacourse.shoppingcart.service;

import com.javacourse.shoppingcart.model.ShoppingCar;
import com.javacourse.shoppingcart.repository.ShoppingCarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ShoppingCarService {
    private final ShoppingCarRepository shoppingCarRepository;

    public Mono<ShoppingCar> create(ShoppingCar shoppingCar){
        return shoppingCarRepository.save(shoppingCar);
    }
}
