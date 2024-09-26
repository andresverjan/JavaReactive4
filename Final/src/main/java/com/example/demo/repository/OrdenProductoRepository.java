package com.example.demo.repository;

import com.example.demo.model.Carrito;
import com.example.demo.model.OrdenCompra;
import com.example.demo.model.OrdenProducto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OrdenProductoRepository extends ReactiveCrudRepository<OrdenProducto, Long> {
    // Método para obtener todos los productos asociados a una orden específica
    Flux<OrdenProducto> findByOrdenId(Long ordenId);

    // Método para eliminar los productos asociados a una orden
    Mono<Void> deleteByOrdenId(Long ordenId);
}
