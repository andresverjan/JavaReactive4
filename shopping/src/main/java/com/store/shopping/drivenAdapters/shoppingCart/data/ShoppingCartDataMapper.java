package com.store.shopping.drivenAdapters.shoppingCart.data;

import com.store.shopping.entities.ShoppingCart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShoppingCartDataMapper {
    ShoppingCart toModel(ShoppingCartData shoppingCartData);
    ShoppingCartData toRepository(ShoppingCart shoppingCart);
}
