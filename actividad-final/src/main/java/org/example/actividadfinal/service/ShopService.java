package org.example.actividadfinal.service;

import lombok.AllArgsConstructor;
import org.example.actividadfinal.exceptions.ShopException;
import org.example.actividadfinal.model.Product;
import org.example.actividadfinal.model.ResponseData;
import org.example.actividadfinal.model.Shop;
import org.example.actividadfinal.repository.ProductRepository;
import org.example.actividadfinal.repository.ShopRepository;
import org.example.actividadfinal.util.CalculateTotal;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;

    public Mono<ResponseData> addProduct(Shop shop) {
        shop.setCreatedAt(LocalDateTime.now());
        return productRepository.findById(shop.getFkIdProduct())
            .switchIfEmpty(Mono.defer(() -> Mono.error(new ShopException("Producto no existe"))))
            .flatMap(s -> shopRepository.save(shop)
                .flatMap(s1 -> Mono.just(ResponseData
                    .builder()
                    .data(s1)
                    .message("Producto agregado en carrito exitosamente")
                    .build()
        )));
    }

    public Mono<ResponseData> deleteProductByShoppingCart(Long id) {
        return shopRepository.findById(id)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new ShopException("Producto no existe"))))
            .flatMap(s -> shopRepository.deleteById(id))
            .thenReturn(
                    ResponseData
                    .builder()
                    .message("Producto eliminado de carrito exitosamente")
                    .build()
            );
    }

    public Mono<ResponseData> incrementAmount(Map<String, Long> mapRequest){
        return shopRepository.findById(mapRequest.get("id"))
            .flatMap(s -> {
                s.setAmount(mapRequest.get("amount"));
                return shopRepository.save(s);
            })
            .flatMap(s1 -> Mono.just(ResponseData
                    .builder()
                    .data(s1)
                    .message("Cantidad actualizada exitosamente!")
                    .build())
            );
    }

    public Mono<ResponseData> getListOfShoppingCart(Long id){
        return shopRepository.findByIdShop(id)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new ShopException("No se encontraron productos asociados a la compra"))))
            .collectList()
            .flatMap(s -> Mono.just(ResponseData
                .builder()
                .data(s)
                .build())
            );
    }

    public Mono<ResponseData> emptyCart(Long id) {
        return shopRepository.deleteByIdShop(id)
                .thenReturn(ResponseData
                        .builder()
                        .message("Carrito vaciado exitosamente!")
                        .build());

    }

    public Mono<ResponseData> totalShop(Long id) {
        return shopRepository.findByIdShop(id)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new ShopException("No se encontraron productos asociados a la compra"))))
            .flatMap(s -> productRepository.findById(s.getFkIdProduct())
                    .map(p -> p.getPrice() * s.getAmount().doubleValue())
            )
            .collectList()
            .flatMap(CalculateTotal::getTotalOfShop)
            .flatMap(r -> Mono.just(ResponseData
                    .builder()
                    .data(r)
                    .message("Total calculado exitosamente!")
                    .build())
            )
            .onErrorResume(r -> Mono.just(ResponseData.builder()
                    .message("Error calculando el total")
                    .build()));

    }
}
