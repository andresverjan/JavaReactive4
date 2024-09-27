package org.api.repository;

import org.api.model.OrdenCompraProducto;
import org.api.model.OrdenProducto;
import org.api.model.OrdenVenta;
import org.api.model.VentaDto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OrdenVentaProductoRepository extends ReactiveCrudRepository<OrdenCompraProducto, Long> {
    Flux<VentaDto> findOrdenesByProductoId(Long productoId);

}
