package com.bancolombia.shoppingcart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailAmountDTO {


    @NotBlank
    @NotEmpty
    private Long id;

    @NotBlank
    @NotEmpty
    private double amount;
}
