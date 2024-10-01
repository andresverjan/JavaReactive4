package com.example.EntregaFinal.service;

import com.example.EntregaFinal.model.Producto;
import com.example.EntregaFinal.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Flux<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Mono<Producto> buscarProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Mono<Producto> crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Mono<Producto> actualizarProducto(Long id, Producto productoActualizado) {
        return productoRepository.findById(id)
                .flatMap(producto -> {
                    producto.setName(productoActualizado.getName());
                    producto.setPrice(productoActualizado.getPrice());
                    producto.setDescription(productoActualizado.getDescription());
                    producto.setImageUrl(productoActualizado.getImageUrl());
                    producto.setStock(productoActualizado.getStock());
                    return productoRepository.save(producto);
                });
    }

    public Mono<Void> reducirStock(Long id, int cantidad) {
        return productoRepository.findById(id)
                .flatMap(producto -> {
                    if (producto.getStock() >= cantidad) {
                        producto.setStock(producto.getStock() - cantidad);
                        return productoRepository.save(producto).then();
                    } else {
                        return Mono.error(new RuntimeException("Stock insuficiente"));
                    }
                });
    }

    public Mono<Void> eliminarProducto(Long id) {
        return productoRepository.deleteById(id);
    }

}
