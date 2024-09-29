package valko.co.cartmanagament.model.cart;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("carts")
@Builder(toBuilder = true)
public record Cart(
        @Id
        Integer id,
        @Column("user_id")
        Integer userId) {
}
