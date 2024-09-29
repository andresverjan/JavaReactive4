package valko.co.cartmanagament.model.sale;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("sale_orders")
@Builder(toBuilder = true)
public record SaleOrder(
        @Id
        int id,
        Integer userId,
        @Column("creation_date")
        LocalDateTime creationDate,
        @Column("updated_date")
        LocalDateTime updatedDate,
        double total) {
}
