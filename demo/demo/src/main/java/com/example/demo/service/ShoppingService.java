package com.example.demo.service;
import com.example.demo.models.*;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShoppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ShoppingService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShoppingRepository shoppingRepository;


    public Mono<SaveResponse> createShopping(Shopping shopping) {
        shopping.setCreatedAt(LocalDateTime.now());
        shopping.setUpdateAt(LocalDateTime.now());
        return productRepository.findById(Long.valueOf(shopping.getProductId()))
                .flatMap(productFind -> {
                    return shoppingRepository.save(shopping)
                            .flatMap(productSave -> {
                                productFind.setStock(shopping.getStock()+productFind.getStock());
                                return productRepository.save(productFind)
                                        .map(productTable -> new SaveResponse("almacenado con exito",shopping, productTable));
                            });
                }).switchIfEmpty(Mono.just(new SaveResponse("no existe ese producto debes registrarlo primero",shopping,null)));
    }


    public Flux<Shopping> getAllShopping() {
        return shoppingRepository.findAll();
    }


    public Mono<Shopping> updateShopping(Long id, Shopping shopping) {
        return shoppingRepository.findById(id)
                .flatMap(existingShopping -> {
                    int stockActual = existingShopping.getStock();
                    existingShopping.setStock(shopping.getStock());
                    existingShopping.setUpdateAt(LocalDateTime.now());
                    return shoppingRepository.save(existingShopping)
                            .flatMap(product1 -> {
                                return productRepository.findById(Long.valueOf(shopping.getProductId()))
                                        .flatMap(product -> {
                                            int resta = stockActual - shopping.getStock();
                                            if (resta > 0) {
                                                product.setStock(product.getStock() - Math.abs(resta));
                                            } else {
                                                product.setStock(product.getStock() + Math.abs(resta));
                                            }
                                            product.setUpdatedAt(LocalDateTime.now());
                                            return productRepository.save(product).thenReturn(existingShopping);
                                        }).defaultIfEmpty(existingShopping);
                            });
                });
    }



    public Mono<Shopping> deleteShopping(Long id) {
        return shoppingRepository.findById(id)
                .flatMap(deleteShopping -> {
                    int productId = deleteShopping.getProductId();
                    int stock= deleteShopping.getStock();
                    return shoppingRepository.deleteById(id)
                            .then( productRepository.findById(Long.valueOf(productId))
                            .flatMap(product -> {
                                product.setStock(product.getStock()-stock);
                                product.setUpdatedAt(LocalDateTime.now());
                                return productRepository.save(product).thenReturn(deleteShopping);
                                        }).defaultIfEmpty(deleteShopping));
                            });
    }
    public Mono<ShoppingReport> getReport(Dates dates) {
        return shoppingRepository.findByCreatedAtBetween(dates.getInitialDate(),dates.getFinalDate()).collectList().flatMap(shoppings -> {
            ShoppingReport shoppingReport=new ShoppingReport();
            shoppingReport.setOrderNumber(shoppings.size());
            shoppingReport.setShoppingList(shoppings);

            return Mono.just(shoppingReport);
        });
    }
}
