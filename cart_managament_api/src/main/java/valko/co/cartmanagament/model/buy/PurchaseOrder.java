package valko.co.cartmanagament.model.buy;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import valko.co.cartmanagament.model.products.Product;

import java.time.LocalDateTime;
import java.util.List;

@Table("purchase_orders")
@Builder(toBuilder = true)
public record PurchaseOrder(
        @Id
        int id,
        List<Product> products,
        @Column("creation_date")
        LocalDateTime creationDate,
        @Column("updated_date")
        LocalDateTime updatedDate,
        double total) {
}
