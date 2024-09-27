package org.api.repository;

import org.api.model.PurchaseDto;
import org.api.model.PurchaseProduct;
import org.api.model.SaleProduct;
import org.api.model.SaleDto;
import org.api.model.SalesOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface PurchaseProductRepository extends ReactiveCrudRepository<PurchaseProduct, Long> {
    Flux<PurchaseDto> findOrdenesByProductoId(Long productoId);
    Flux<PurchaseProduct> findAllByOrdenId(Long productoId);

}
