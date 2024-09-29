package valko.co.cartmanagament.web.sales.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
public record SaleOrderDTO(
        Integer userId,
        Integer cartId,
        List<ProductDTO> products,
        double total,
        LocalDateTime date) {
}
