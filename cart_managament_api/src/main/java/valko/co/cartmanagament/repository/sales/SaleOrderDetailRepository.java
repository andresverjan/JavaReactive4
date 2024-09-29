package valko.co.cartmanagament.repository.sales;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import valko.co.cartmanagament.model.report.TopProduct;
import valko.co.cartmanagament.model.sale.SaleOrderDetail;

import java.time.LocalDateTime;

@Repository
public interface SaleOrderDetailRepository extends ReactiveCrudRepository<SaleOrderDetail, Integer> {

    @Query("""
                SELECT sod.sale_order_id, sod.product_id, sod.amount
                FROM sale_orders so
                JOIN sale_order_products sod
                ON so.id=sod.sale_order_id
                WHERE sod.product_id=:productId
            """)
    Flux<SaleOrderDetail> findOrderDetailByProductId(int productId);

    @Query("""
            SELECT sod.sale_order_id, sod.product_id, sod.amount
            FROM sale_orders so
            JOIN sale_order_products sod ON so.id = sod.sale_order_id
            WHERE so.creation_date BETWEEN :startDate AND :endDate
           """)
    Flux<SaleOrderDetail> findOrderDetailsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    @Query("""
            SELECT sod.product_id, SUM(sod.amount) as total_amount
            FROM sale_orders so
            JOIN sale_order_products sod ON so.id = sod.sale_order_id
            WHERE so.creation_date BETWEEN :startDate AND :endDate
            GROUP BY sod.product_id
            ORDER BY total_amount DESC
            LIMIT 5
           """)
    Flux<TopProduct> findTop5ProductsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}
