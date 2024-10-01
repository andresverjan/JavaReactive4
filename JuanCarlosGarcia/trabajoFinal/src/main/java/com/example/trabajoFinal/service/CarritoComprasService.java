package com.example.trabajoFinal.service;

import com.example.trabajoFinal.exception.CustomException;
import com.example.trabajoFinal.model.CarritosCompras;
import com.example.trabajoFinal.model.OrdenesVentas;
import com.example.trabajoFinal.model.TotalCompra;
import com.example.trabajoFinal.repository.CarritoComprasRepository;
import com.example.trabajoFinal.repository.PersonaRepository;
import com.example.trabajoFinal.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarritoComprasService {
    private final List<CarritosCompras> carritosCompras = new ArrayList<>();
    private final ProductRepository productRepository;
    private final CarritoComprasRepository carritoComprasRepository;
    private final PersonaRepository personaRepository;
    private final OrdenesVentasService ordenesVentasService;

    public CarritoComprasService(ProductRepository productRepository, CarritoComprasRepository carritoComprasRepository,
                                 PersonaRepository personaRepository, OrdenesVentasService ordenesVentasService) {
        this.productRepository = productRepository;
        this.carritoComprasRepository = carritoComprasRepository;
        this.personaRepository = personaRepository;
        this.ordenesVentasService = ordenesVentasService;
    }

    public Mono<CarritosCompras> addProductToCart(CarritosCompras carritoCompras) {
        return personaRepository.findById(carritoCompras.getPersonaId())
                .switchIfEmpty(Mono.error(new CustomException("Persona no encontrada")))
                .then(productRepository.findById(carritoCompras.getProductId()))
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado")))
                .flatMap(product -> {
                    if (product.getStock() < carritoCompras.getCantidad()) {
                        return Mono.error(new CustomException("No hay suficiente stock del producto, " +
                                "stock actual " + product.getStock()));
                    }
                    return productRepository.save(product);
                })
                .flatMap(product -> {
                    CarritosCompras item = new CarritosCompras();
                    item.setPersonaId(carritoCompras.getPersonaId());
                    item.setProductId(carritoCompras.getProductId());
                    item.setNameProduct(product.getName());
                    item.setCantidad(carritoCompras.getCantidad());
                    item.setPrice(product.getPrice());
                    item.setEstado("Agregado");
                    return carritoComprasRepository.save(item);
                });
    }

    public Mono<String> removeProductFromCart(Integer productId, Integer personaId) {
        return personaRepository.findById(personaId)
                .switchIfEmpty(Mono.error(new CustomException("Persona no tiene items en el carrito")))
                .then(carritoComprasRepository.findByProductIdAndPersonaId(productId, personaId))
                .switchIfEmpty(Mono.error(new CustomException("Producto no encontrado en el carrito")))
                .flatMap(carritoComprasRepository::delete)
                .then(Mono.just("Producto eliminado del carrito"));
    }

    public Mono<CarritosCompras> updateProductToCart(CarritosCompras carritoCompras) {
        return personaRepository.findById(carritoCompras.getPersonaId())
                .switchIfEmpty(Mono.error(new CustomException("Persona no tiene items en el carrito")))
                .then(carritoComprasRepository.findByProductIdAndPersonaId(carritoCompras.getProductId(), carritoCompras.getPersonaId()))
                .switchIfEmpty(Mono.error(new CustomException("Producto no encontrado en el carrito")))
                .flatMap(item -> productRepository.findById(carritoCompras.getProductId())
                        .flatMap(product -> {
                            if (product.getStock() + item.getCantidad() - carritoCompras.getCantidad() < 0) {
                                return Mono.error(new CustomException("No hay suficiente stock del producto, " +
                                        "stock actual " + product.getStock()));
                            }
                            item.setCantidad(carritoCompras.getCantidad());
                            item.setPrice(product.getPrice());
                            item.setEstado("Actualizado");
                            return productRepository.save(product)
                                    .then(carritoComprasRepository.save(item));
                        }));
    }

    public Flux<CarritosCompras> getCartItems(Integer personaId) {
        return carritoComprasRepository.findByPersonaId(personaId)
                .switchIfEmpty(Mono.error(new CustomException("Persona no tiene items en el carrito")));
    }

    public Mono<String> emptyCart(Integer personaId) {
        return carritoComprasRepository.findByPersonaId(personaId)
                .switchIfEmpty(Mono.error(new CustomException("Persona no tiene items en el carrito")))
                .flatMap(carritoComprasRepository::delete)
                .then(Mono.just("Carrito Vacio"));
    }

    public Mono<TotalCompra> calculateTotal(Integer personaId, TotalCompra totalCompra) {
        Flux<CarritosCompras> items = carritoComprasRepository.findByPersonaId(personaId);
        Mono<Double> total = items
                .map(item -> item.getPrice() * item.getCantidad())
                .reduce(0.0, Double::sum)
                .map(subTotal -> subTotal + subTotal * totalCompra.getIva() + totalCompra.getCostoEnvio());
        return items.collectList()
                .zipWith(total, (itemList, totalValue) ->
                        new TotalCompra(itemList,
                                totalCompra.getIva(),
                                totalCompra.getCostoEnvio(),
                                totalValue));
    }

    public Flux<String> createOrdenFromCart(Integer personaId) {
        return carritoComprasRepository.findByPersonaId(personaId)
                .collectList()
                .flatMapMany(items -> {
                    return Flux.fromIterable(items)
                            .flatMap(item -> {
                                OrdenesVentas ordenesVentas = new OrdenesVentas();
                                ordenesVentas.setPersonaId(personaId);
                                ordenesVentas.setProductId(item.getProductId());
                                ordenesVentas.setCantidad(item.getCantidad());
                                return ordenesVentasService.createOrdenesVentas(ordenesVentas);
                            });
                })
                .flatMap(ordenVenta -> {
                    // Actualizar el estado de los items en el carrito a "Comprado"
                    return carritoComprasRepository.findByPersonaId(personaId)
                            .flatMap(item -> carritoComprasRepository.findById(item.getId())
                                    .flatMap(carritoItem -> {
                                        carritoItem.setEstado("Comprado");
                                        return carritoComprasRepository.save(carritoItem);
                                    })
                            )
                            .then(Mono.just("Orden de venta creada exitosamente"));
                });
    }
}
