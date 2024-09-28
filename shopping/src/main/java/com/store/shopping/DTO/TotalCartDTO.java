package com.store.shopping.DTO;

import com.store.shopping.entities.ProductsInCart;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class TotalCartDTO {
    List<ProductsInCart> products;
    Double total;
}
