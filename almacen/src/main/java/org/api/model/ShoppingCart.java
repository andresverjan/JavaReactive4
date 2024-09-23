package org.api.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Mono;

import java.util.Map;

@Data
@Builder
@Table("api.carritos")
public class ShoppingCart {


    @Id
    private String id;
    @Column("client_id")
    private String clientId;
    @Column("items")
    private Map<Long, Integer> items;
    @Column("total")
    @Builder.Default
    private Double total = 0.0;

    public Mono<Object> agregarProducto(Long productoId, int cantidad) {
        items.put(productoId, items.getOrDefault(productoId, 0) + cantidad);
        return null;
    }

    public void eliminarProducto(Long productoId) {
        items.remove(productoId);
    }

    public void actualizarCantidad(Long productoId, Integer cantidad) {
        if(items.containsKey(productoId)){
        if (cantidad <= 0) {
            eliminarProducto(productoId);
        } else {
            items.put(productoId, cantidad);
        }}
    }

    public void vaciar() {
        items.clear();
    }

}
