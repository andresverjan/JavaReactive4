package com.bancolombia.shoppingcart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table("cart_master")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    private Long id;

    @Column("subtotalorder")
    private double subtotalorder;

    @Column("totaldiscount")
    private double totaldiscount;

    @Column("totaltax")
    private double totaltax;

    @Column("createdat")
    private LocalDateTime createdAt;

    @Column("updatedat")
    private LocalDateTime updatedAt;

    @Column("userid")
    private Long userid;

    @Transient
    List<CartDetail> cartDetailList;

    public List<CartDetail> getCartDetail(){
        return  cartDetailList == null ? new ArrayList<>() : cartDetailList;
    }
}
