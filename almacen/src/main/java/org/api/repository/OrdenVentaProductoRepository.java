package org.api.repository;

import org.api.model.PurchaseProduct;
import org.api.model.SaleDto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OrdenVentaProductoRepository extends ReactiveCrudRepository<PurchaseProduct, Long> {
    Flux<SaleDto> findOrdenesByProductoId(Long productoId);

}