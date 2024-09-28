package com.store.shopping.drivenAdapters.salesOrder;

import com.store.shopping.DTO.TopFiveDTO;
import com.store.shopping.drivenAdapters.salesOrder.data.SalesData;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.sql.Timestamp;

public interface SalesRepository  extends ReactiveCrudRepository<SalesData, Integer> {
    @Query("SELECT * FROM sales WHERE created_at>=:init AND created_at<=:end")
    Flux<SalesData> getSalesInRange(Timestamp init, Timestamp end);
    @Query("SELECT SUM(c.quantity) AS quantity, p.name AS name FROM shoppingcart AS c " +
            "JOIN products AS p ON c.product=p.id " +
            "JOIN sales as s ON c.id=s.cartItemId"+
            " WHERE c.status='S' AND s.created_at>=:init AND s.created_at<=:end GROUP BY (name)" +
            " ORDER BY quantity desc limit 5")
    Flux<TopFiveDTO> getTopFive(Timestamp init,Timestamp end);

}
