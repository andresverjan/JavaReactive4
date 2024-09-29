package valko.co.cartmanagament.web.cart.dto;

import lombok.Builder;
import valko.co.cartmanagament.model.cart.CartItem;
import java.util.List;

@Builder(toBuilder = true)
public record CartDTO(
        Integer userId,
        List<CartItem> items) {
}
