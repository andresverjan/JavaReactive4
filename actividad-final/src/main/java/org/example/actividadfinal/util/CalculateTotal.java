package org.example.actividadfinal.util;

import reactor.core.publisher.Mono;

import java.util.List;

public class CalculateTotal {

    public static Mono<Double> getTotalOfShop(List<Double> list) {
        return Mono.just(list)
            .map(values -> values.stream().mapToDouble(Double::doubleValue).sum())
            .flatMap(res -> Mono.just(res + (res * 0.19))) // Add IVA 19%
            .flatMap(res1 -> Mono.just(res1 + 130));
    }
}
