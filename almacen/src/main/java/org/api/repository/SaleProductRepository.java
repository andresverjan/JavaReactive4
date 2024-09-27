package org.api.repository;

import org.api.model.SaleDto;
import org.api.model.SaleProduct;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface SaleProductRepository extends ReactiveCrudRepository<SaleProduct, Long> {
    Flux<SaleDto> findOrdenesByProductoId(Long productoId);

}
