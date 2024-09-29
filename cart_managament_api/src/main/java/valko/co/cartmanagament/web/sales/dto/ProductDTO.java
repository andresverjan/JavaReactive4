package valko.co.cartmanagament.web.sales.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record ProductDTO(
        String name,
        double price,
        int amount) {
}
