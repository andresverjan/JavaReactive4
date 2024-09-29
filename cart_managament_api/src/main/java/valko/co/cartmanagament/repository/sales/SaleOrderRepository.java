package valko.co.cartmanagament.repository.sales;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import valko.co.cartmanagament.model.sale.SaleOrder;

@Repository
public interface SaleOrderRepository extends ReactiveCrudRepository<SaleOrder, Integer> {

}
