package org.api.repository;

import org.api.model.OrdenVenta;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface VentasRepository extends ReactiveCrudRepository<OrdenVenta, Long> {

}
