package com.store.shopping.drivenAdapters.shoppingCart.data;

import com.store.shopping.entities.enums.ShoppingCartEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder(toBuilder = true)
@Table("shoppingCart")
public class ShoppingCartData {
    @Id
    private Integer id;
    @Column("buyer")
    private String buyer;
    @Column("quantity")
    private Integer quantity;
    @Column("status")
    private ShoppingCartEnum status;
    @Column("product")
    private Integer product;
}
