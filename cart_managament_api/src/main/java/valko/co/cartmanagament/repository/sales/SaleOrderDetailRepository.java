package valko.co.cartmanagament.repository.sales;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import valko.co.cartmanagament.model.sale.SaleOrderDetail;

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

}
