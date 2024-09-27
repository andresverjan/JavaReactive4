package org.api.repository;


import org.api.model.OrdenCompra;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ComprasRepository extends ReactiveCrudRepository<OrdenCompra, Long> {
    Flux<OrdenCompra> findAllByEstado(String estado);
}
