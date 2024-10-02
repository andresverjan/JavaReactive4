package com.curso.java.reactor.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CartProductDTO {
    private int quantity;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long cartId;
    private ProductDTO product;
}
