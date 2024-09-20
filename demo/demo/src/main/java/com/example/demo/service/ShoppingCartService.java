package com.example.demo.service;

import com.example.demo.models.Product;
import com.example.demo.models.ResponseTotal;
import com.example.demo.models.ShoppingCart;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ShoppingCartService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public Mono<ShoppingCart> addProduct(ShoppingCart shoppingCart) {
        shoppingCart.setCreatedAt(LocalDateTime.now());
        shoppingCart.setUpdateAt(LocalDateTime.now());

        return productRepository.findById(Long.valueOf(shoppingCart.getProductId())).flatMap(p->
                shoppingCartRepository.findByCartIdAndProductId(shoppingCart.getCartId(),shoppingCart.getProductId())
                        .flatMap(shoppingFind -> {
                            shoppingFind.setAmount(shoppingFind.getAmount() + shoppingCart.getAmount());
                            double price=p.getPrice();
                            shoppingFind.setPrice(shoppingFind.getPrice()+price*shoppingCart.getAmount());
                            return shoppingCartRepository.save(shoppingFind)
                                    .flatMap(updateShopping -> updateProduct((long) shoppingCart.getProductId(), shoppingCart.getAmount())).thenReturn(shoppingCart);
                        })
                        .switchIfEmpty( Mono.just(p)
                                        .map(product -> {
                                            shoppingCart.setPrice(p.getPrice()*shoppingCart.getAmount());
                                            return p;
                                        }).flatMap( product1->{
                                    return shoppingCartRepository.save(shoppingCart).flatMap(savedShopping ->
                                            updateProduct((long) shoppingCart.getProductId(), shoppingCart.getAmount())).thenReturn(shoppingCart);
                                })));



    }

    private Mono<Product> updateProduct(Long productId, int amount) {
        return productRepository.findById(productId)
                .flatMap(product -> {
                    product.setStock(product.getStock()-amount);
                    return productRepository.save(product);
                });
    }
    public Mono<ShoppingCart> deleteProduct(Long id , int productId) {
        return shoppingCartRepository.findByCartIdAndProductId(Math.toIntExact(id),productId)
                .flatMap(deleteShoppingCart -> {
                    int amount=deleteShoppingCart.getAmount();
                    return shoppingCartRepository.deleteById(Long.valueOf(deleteShoppingCart.getId()))
                            .then( productRepository.findById(Long.valueOf(productId))
                                    .flatMap(product -> {
                                        product.setStock(product.getStock()+amount);
                                        product.setUpdatedAt(LocalDateTime.now());
                                        return productRepository.save(product).thenReturn(deleteShoppingCart);
                                    }).defaultIfEmpty(deleteShoppingCart));
                });
    }
    public Mono<ShoppingCart> updateShopping(Long id, ShoppingCart shopping) {
        return shoppingCartRepository.findById(id)
                .flatMap(existingShoppingCart -> {
                    int stockActual = existingShoppingCart.getAmount();
                    existingShoppingCart.setAmount(shopping.getAmount());
                    existingShoppingCart.setUpdateAt(LocalDateTime.now());
                    return shoppingCartRepository.save(existingShoppingCart)
                            .flatMap(product1 -> {
                                return productRepository.findById(Long.valueOf(shopping.getProductId()))
                                        .flatMap(product -> {
                                            int resta = stockActual - shopping.getAmount();
                                            if (resta > 0) {
                                                product.setStock(product.getStock() + Math.abs(resta));
                                            } else {
                                                product.setStock(product.getStock() - Math.abs(resta));
                                            }
                                            product.setUpdatedAt(LocalDateTime.now());
                                            return productRepository.save(product).thenReturn(existingShoppingCart);
                                        }).defaultIfEmpty(existingShoppingCart);
                            });
                });
    }
    public Flux<ShoppingCart> getProductsCart(Long id) {
        return shoppingCartRepository.findByCartId(Math.toIntExact(id));
    }
    public Mono<Double> calculateTotal(Long id) {
        return shoppingCartRepository.findByCartId(Math.toIntExact(id)).map(ShoppingCart::getPrice).reduce(0.0,Double::sum);
        };


    public Mono<Void> emptyCart(Long id) {
        return shoppingCartRepository.deleteByCartId(id);
    }


}
