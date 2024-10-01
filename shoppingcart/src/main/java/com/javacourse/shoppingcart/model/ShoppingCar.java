package com.javacourse.shoppingcart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("TBL_SHOPPING_CAR")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCar extends Auditing {
    @Id
    private Long id;

    @Column("user_id")
    private User user;

}
