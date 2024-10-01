package com.example.EntregaFinal.repository;

import com.example.EntregaFinal.model.OrdenProducto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository

public interface OrdenProductoRepository extends ReactiveCrudRepository<OrdenProducto, Long> {

    Flux<OrdenProducto> findByOrdenId(Long ordenId);

}
