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

@Table("carrito_item")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItemEntity {
    @Id
    @Column("id")
    private Integer id;
    @Column("id_carrito")
    private Integer cartId;  // Relación con ShoppingCartEntity
    @Column("id_producto")
    private Integer productId;  // Relación con ProductEntity
    @Column("cantidad")
    private int quantity;

    @Transient  // Asume que no se almacena en la base de datos si no existe la columna "precio"
    private double price;  // Precio del producto, necesario para calcular el total

    // Constructor completo
    public CartItemEntity(Integer id, Integer cartId, Integer productId, Integer quantity, Double price) {
        this.id = id;
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
}
