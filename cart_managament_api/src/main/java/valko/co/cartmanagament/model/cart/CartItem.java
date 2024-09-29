package valko.co.cartmanagament.model.cart;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("cart_items")
@Builder(toBuilder = true)
public record CartItem(
        @Id
        Integer id,
        @Column("cart_id")
        Integer cartId,
        @Column("product_id")
        Integer product,
        int amount) {
}
