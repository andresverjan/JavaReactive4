package org.api.repository;

import org.api.model.OrdenProducto;
import org.api.model.VentaDto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OrdenProductoRepository extends ReactiveCrudRepository<OrdenProducto, Long> {
    Flux<VentaDto> findOrdenesByProductoId(Long productoId);

}
