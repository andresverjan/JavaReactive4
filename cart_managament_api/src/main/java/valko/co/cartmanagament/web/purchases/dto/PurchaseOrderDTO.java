package valko.co.cartmanagament.web.purchases.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record PurchaseOrderDTO(
        Integer productId,
        String name,
        double price,
        int amount
) {
}
