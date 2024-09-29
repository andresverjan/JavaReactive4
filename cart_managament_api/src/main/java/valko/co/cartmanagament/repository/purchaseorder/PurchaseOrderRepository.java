package valko.co.cartmanagament.repository.purchaseorder;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import valko.co.cartmanagament.model.buy.PurchaseOrder;

@Repository
public interface PurchaseOrderRepository extends ReactiveCrudRepository<PurchaseOrder, Integer> {
    Flux<PurchaseOrder> findAllByProductsId(int productId);
}
