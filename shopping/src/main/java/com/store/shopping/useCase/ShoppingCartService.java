package com.store.shopping.useCase;

import com.store.shopping.drivenAdapters.shoppingCart.ShoppingCartImpl;
import com.store.shopping.entities.Product;
import com.store.shopping.entities.ProductsInCart;
import com.store.shopping.entities.ShoppingCart;
import com.store.shopping.entities.enums.ShoppingCartEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    @Autowired
    private final ShoppingCartImpl shoppingCartImpl;
    @Autowired
    private final ProductService productService;

    public Mono<ShoppingCart> AddItemToCart(ShoppingCart shoppingCart){
        return shoppingCartImpl.addProductToCart(shoppingCart);
    }
    public Mono<ShoppingCart> removeItemToCart(Integer id){
        return shoppingCartImpl.getShoppingCartItem(id)
                .map(item->{
                    item.setStatus(ShoppingCartEnum.R);
                    return item;
                })
                .flatMap(item->shoppingCartImpl.updateItem(id, item));
    }
    public Mono<ShoppingCart> sellItemToCart(Integer id,ShoppingCart shoppingCart){
        shoppingCart.setStatus(ShoppingCartEnum.S);
        return shoppingCartImpl.updateItem(id,shoppingCart);
    }
    public Flux<ProductsInCart> getShoppingCartItemsActiveByBuyer(String buyer){
        return shoppingCartImpl.getProductsInCartByBuyer(buyer);
    }
    public Flux<Tuple2<Product,ShoppingCart>> sellCartItemsByBuyer(String buyer){
        return shoppingCartImpl.getShoppingCartItemsActiveByBuyer(buyer)
                .flatMap(item->{
                    item.setStatus(ShoppingCartEnum.S);
                    return Mono.zip(productService.sellProduct(item.getProduct(), item.getQuantity()),
                            shoppingCartImpl.updateItem(item.getId(), item));
                })
                .onErrorResume(e -> {
                    return Mono.error(new CustomServiceException("Error en la venta de productos "+e));
                });


    }
    public Mono<ShoppingCart> updateQuantity(Integer id,ShoppingCart shoppingCart){
        return shoppingCartImpl.getShoppingCartItem(id)
                .map(item->{
                    if(item.getStatus()==ShoppingCartEnum.S){
                        throw new CustomServiceException("El producto está en estado vendido, no se puede actualizar");
                    }
                    item.setQuantity(shoppingCart.getQuantity());
                    return item;
                })
                .flatMap(item->shoppingCartImpl.updateItem(id, item));
    }
    public Flux<ShoppingCart> emptyCart(String buyer){
        return shoppingCartImpl.getShoppingCartItemsActiveByBuyer(buyer)
                .map(item->{
                    item.setStatus(ShoppingCartEnum.R);
                    return item;
                })
                .flatMap(item->shoppingCartImpl.updateItem(item.getId(), item));
    }
    public Mono<Tuple2<List<ProductsInCart>, Double>> getCartPrice(String buyer){
        return Mono.zip(shoppingCartImpl.getProductsInCartByBuyer(buyer).collectList(),calculatePrice(buyer));
    }
    private Mono<Double> calculatePrice(String buyer){
        return shoppingCartImpl.getProductsInCartByBuyer(buyer)
                .map(item->item.getPrice()* item.getQuantity())
                .reduce(0.0,Double::sum);
    }
    public static class CustomServiceException extends RuntimeException {
        public CustomServiceException(String message) {
            super(message);
        }
    }
}
