package compras.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
@Table("carrito")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShoppingCartEntity {
    @Id
    @Column("id_carrito")
    private Integer id;
    @Column("producto")
    private String product;
    @Column("fecha_creacion")
    private LocalDateTime createdAt;
    @Column("fecha_modificacion")
    private LocalDateTime updatedAt;
    @Column("estado")
    private String status;
    @Column("id_producto")
    private Integer productId;
    @Transient  // No se almacena en la base de datos
    private List<CartItemEntity> items = new ArrayList<>();

    // Constructor para crear un nuevo carrito
    public ShoppingCartEntity(Integer id, LocalDateTime createdAt, LocalDateTime updatedAt, String status, Integer productId) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.productId = productId;
        this.items = new ArrayList<>();
    }

    public void removeItem(Integer productId) {
        boolean removed = items.removeIf(item -> item.getProductId() == productId);
        if (!removed) {
            throw new RuntimeException("El ítem con ID " + productId + " no se encontró en el carrito.");
        }
    }

    // Actualizar la cantidad de un ítem en el carrito
    public void updateItemQuantity(Integer productId, Integer quantity) {
        items.stream()
                .filter(item -> item.getProductId() == productId)
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));
    }

    // Vaciar el carrito
    public void emptyCart() {
        items.clear();
    }

    public double calculateTotal() {
        return items.stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
    }
    public double calculateTotalItems() {
        return items.stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
    }

    public List<CartItemEntity> getItems() {
        return items;
    }
}
