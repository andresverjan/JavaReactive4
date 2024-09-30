package org.example.actividadfinal.repository;

import io.r2dbc.spi.Row;
import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class ReportRepository{

    private final DatabaseClient databaseClient;


    public Flux<Map<String, Object>> getPurchaseOrderByDateRange(LocalDateTime startTime, LocalDateTime endTime) {
        return databaseClient.sql("""
                SELECT OC.id_orden, OC.fk_id_compra, CO.cantidad, CO.tipo, PR.* 
                FROM reactive.ordenes_compra OC
                INNER JOIN reactive.compra CO ON CO.id_compra = OC.fk_id_compra
                INNER JOIN reactive.producto PR ON PR.id_producto = CO.fk_id_producto
                WHERE OC.fecha > :startTime::timestamp without time zone AND OC.fecha < :endTime::timestamp without time zone
            """)
            .bind("startTime", startTime)
            .bind("endTime", endTime)
            .map((row, metadata) -> {
                Map<String, Object> map = new HashMap<>();
                map.put("orderId", Objects.requireNonNull(row.get(0)));
                map.put("fkIdShop", Objects.requireNonNull(row.get(1)));
                map.put("amount", Objects.requireNonNull(row.get(2)));
                map.put("type", Objects.requireNonNull(row.get(3)));
                map.put("idProduct", Objects.requireNonNull(row.get(4)));
                map.put("name", Objects.requireNonNull(row.get(5)));
                map.put("value", Objects.requireNonNull(row.get(6)));
                map.put("description", Objects.requireNonNull(row.get(7)));
                map.put("stock", Objects.requireNonNull(row.get(9)));
                map.put("createDate", Objects.requireNonNull(row.get(10)));
                map.put("fkIdUser", Objects.requireNonNull(row.get(12)));
                return map;
            }) // You can process rows as needed
            .all();
    }


    public Flux<Map<String, Object>> getSalesOrderByDateRange(LocalDateTime startTime, LocalDateTime endTime) {
        return databaseClient.sql("""
                SELECT OV.id_orden, OV.fk_id_compra, CO.cantidad, CO.tipo, PR.* FROM reactive.ordenes_venta OV
                INNER JOIN reactive.compra CO ON CO.id_compra = OV.fk_id_compra
                INNER JOIN reactive.producto PR ON PR.id_producto = CO.fk_id_producto
                WHERE OV.fecha > :startTime::timestamp without time zone AND OV.fecha < :endTime::timestamp without time zone
            """)
                .bind("startTime", startTime)
                .bind("endTime", endTime)
                .map((row, metadata) -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("orderId", Objects.requireNonNull(row.get(0)));
                    map.put("fkIdShop", Objects.requireNonNull(row.get(1)));
                    map.put("amount", Objects.requireNonNull(row.get(2)));
                    map.put("type", Objects.requireNonNull(row.get(3)));
                    map.put("idProduct", Objects.requireNonNull(row.get(4)));
                    map.put("name", Objects.requireNonNull(row.get(5)));
                    map.put("value", Objects.requireNonNull(row.get(6)));
                    map.put("description", Objects.requireNonNull(row.get(7)));
                    map.put("createDate", Objects.requireNonNull(row.get(10)));
                    map.put("fkIdUser", Objects.requireNonNull(row.get(12)));
                    return map;
                }) // You can process rows as needed
                .all();
    }

    public Flux<Map<String, Object>> topFiveOfProducts() {
        return databaseClient.sql("""
                SELECT PR.nombre, SUM(CO.cantidad) AS total_sales
                FROM reactive.ordenes_venta OV
                INNER JOIN reactive.compra CO ON CO.id_compra = OV.fk_id_compra
                INNER JOIN reactive.producto PR ON PR.id_producto = CO.fk_id_producto
                GROUP BY 1
                ORDER BY total_sales DESC
                LIMIT 5;
            """)
                .map((row, metadata) -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", Objects.requireNonNull(row.get(0)));
                    map.put("totalSales", Objects.requireNonNull(row.get(1)));
                    return map;
                }) // You can process rows as needed
                .all();
    }
}
