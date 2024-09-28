package com.programacion.reactiva.trabajo_final.service;

import com.programacion.reactiva.trabajo_final.model.Producto;
import com.programacion.reactiva.trabajo_final.repository.ProductoRepository;
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

    public Mono<Producto> crearProducto(Producto producto) {
        return productoRepository.save(producto)
                .doOnNext(product -> System.out.println("Producto creado: " + producto));
    }

    public Mono<Producto> editarProducto(Long id, Producto producto) {
        return productoRepository.findById(id)
                .flatMap(editarProducto -> {
                    editarProducto.setName(producto.getName());
                    editarProducto.setPrice(producto.getPrice());
                    editarProducto.setDescription(producto.getDescription());
                    editarProducto.setImageUrl(producto.getImageUrl());
                    editarProducto.setStock(producto.getStock());
                    return productoRepository.save(editarProducto);
                });
    }

    public Mono<Producto> buscarProducto(Long id){
        return productoRepository.findById(id);
    }

    public Flux<Producto> buscarProductoPorNombre(String nombre){
        return productoRepository.findByNameContaining(nombre);
    }

    public Mono<Void> eliminarProducto(Long id){
        return productoRepository.deleteById(id);
    }

    public Mono<Producto> actualizarStock(Long id, int cantidad){
        return productoRepository.findById(id)
                .flatMap(producto -> {
                    System.out.println("Actualizando stock del producto: " + producto.getName());
                    producto.setStock(cantidad);
                    return productoRepository.save(producto);
                });
    }

}
