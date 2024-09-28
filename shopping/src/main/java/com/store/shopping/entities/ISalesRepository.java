package com.store.shopping.entities;

import com.store.shopping.DTO.TopFiveDTO;
import reactor.core.publisher.Flux;

public interface ISalesRepository {
    Flux<Sales> saleProductsInCart(String client);
    Flux<Sales> salesReportInDateRange(String initDate,String endDate);
    Flux<TopFiveDTO> topFiveReportInDateRange(String initDate, String endDate);
}
