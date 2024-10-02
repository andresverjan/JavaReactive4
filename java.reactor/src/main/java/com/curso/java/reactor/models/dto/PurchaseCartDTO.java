package com.curso.java.reactor.models.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseCartDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long cartId;
    private List<CartProductDTO> items;
}
