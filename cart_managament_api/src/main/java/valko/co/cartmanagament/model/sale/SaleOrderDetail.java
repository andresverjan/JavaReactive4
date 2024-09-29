package valko.co.cartmanagament.model.sale;

import lombok.Builder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("sale_order_products")
@Builder(toBuilder = true)
public record SaleOrderDetail(
        @Column("sale_order_id")
        Integer saleOrderId,
        @Column("product_id")
        Integer productId,
        double amount) {
}
