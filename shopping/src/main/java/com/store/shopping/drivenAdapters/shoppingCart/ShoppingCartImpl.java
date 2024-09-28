package com.store.shopping.drivenAdapters.shoppingCart;

import com.store.shopping.drivenAdapters.shoppingCart.data.ShoppingCartDataMapper;
import com.store.shopping.entities.IShoppingCartRepository;
import com.store.shopping.entities.ProductsInCart;
import com.store.shopping.entities.ShoppingCart;
import com.store.shopping.entities.enums.ShoppingCartEnum;
import com.store.shopping.useCase.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ShoppingCartImpl implements IShoppingCartRepository {
    @Autowired
    private final ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private final ShoppingCartDataMapper shoppingCartDataMapper;
    @Override
    public Mono<ShoppingCart> addProductToCart(ShoppingCart shoppingCart) {
        shoppingCart.setStatus(ShoppingCartEnum.A);
        return shoppingCartRepository.save(shoppingCartDataMapper.toRepository(shoppingCart))
                .map(shoppingCartDataMapper::toModel);
    }

    @Override
    public Mono<ShoppingCart> updateItem(Integer id, ShoppingCart shoppingCart) {
        return shoppingCartRepository.findById(id)
                .flatMap(item->{
                    item.setStatus(shoppingCart.getStatus());
                    item.setQuantity(shoppingCart.getQuantity());
                    return shoppingCartRepository.save(item);
                })
                .map(shoppingCartDataMapper::toModel)
                .onErrorResume(e -> Mono.error(new ShoppingCartService.CustomServiceException("Error en la venta de productos "+e)));
    }

    @Override
    public Mono<ShoppingCart> getShoppingCartItem(Integer id) {
        return shoppingCartRepository.findById(id)
                .map(shoppingCartDataMapper::toModel);
    }

    @Override
    public Flux<ShoppingCart> getShoppingCartItemsActiveByBuyer(String buyer) {
        return shoppingCartRepository.getActiveItemsByBuyer(buyer)
                .map(shoppingCartDataMapper::toModel);
    }

    @Override
    public Flux<ProductsInCart> getProductsInCartByBuyer(String buyer) {
        return shoppingCartRepository.getItemsToBuyByBuyer(buyer)
                .map(shoppingCartDTO -> {
                    return ProductsInCart.builder()
                            .id(shoppingCartDTO.getId())
                            .product(shoppingCartDTO.getProduct())
                            .name(shoppingCartDTO.getName())
                            .Price(shoppingCartDTO.getPrice())
                            .quantity(shoppingCartDTO.getQuantity())
                            .build();
                });
    }

}
