package valko.co.cartmanagament.model.products;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("products")
@Builder(toBuilder = true)
public record Product(
        @Id
        int id,
        String name,
        double price,
        String description,
        @Column("image_url")
        String imageUrl,
        int stock,
        @Column("created_at")
        LocalDateTime createdAt,
        @Column("updated_at")
        LocalDateTime updatedAt) {
}
