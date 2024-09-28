package com.store.shopping.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class TopFiveDTO {
    private Integer quantity;
    private String name;
}
