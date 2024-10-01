package com.javacourse.shoppingcart.service;

import com.javacourse.shoppingcart.model.ProductCar;
import com.javacourse.shoppingcart.model.ShoppingCar;
import com.javacourse.shoppingcart.repository.ProductCarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductCarService {
    private final ProductCarRepository  productCarRepository;

    public Mono<ProductCar> create(ProductCar productCar){
        return productCarRepository.save(productCar);
    }

    public Mono<Void> delete (ProductCar productCar){
        //TODO:Buscar primero el producto y luego eliminarlo (Validar)
        return productCarRepository.delete(productCar);
    }

    public Mono<String> update (ProductCar productCar){
        if(productCar.getId()!=null){
            productCar.setUpdatedDate(LocalDateTime.now());
            return productCarRepository.save(productCar)
                    .doOnNext(productCar1 -> System.out.println("Data updating: "+productCar1) )
                    .then(Mono.just("Product Car Updated"));
        }
        return Mono.just("Product Car is not present ");
    }

    public Flux<ProductCar> getProductByCar (ShoppingCar shoppingCar){
        return  productCarRepository.findAllByShoppingCar(shoppingCar);
    }

    public Mono<Void> deleteAllByShoppingCar(ShoppingCar shoppingCar){
        return productCarRepository.deleteAllByShoppingCar(shoppingCar);
    }

    public Mono<Double> sumTotal(ShoppingCar shoppingCar){
        return productCarRepository.findAllByShoppingCar(shoppingCar).collect(Collectors.summingDouble(value -> value.getPrice())) ;
    }


}
