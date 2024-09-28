package com.store.shopping.drivenAdapters.products.data;

import com.store.shopping.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDataMapper {
    Product toModel(ProductData productData);
    ProductData toRepository(Product product);
}
