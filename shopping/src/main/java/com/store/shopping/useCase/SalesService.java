package com.store.shopping.useCase;

import com.store.shopping.DTO.TopFiveDTO;
import com.store.shopping.drivenAdapters.salesOrder.SalesImpl;
import com.store.shopping.entities.Sales;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class SalesService {
    @Autowired
    private final SalesImpl salesImpl;

    public Flux<Sales> generateSale(String client){
        return salesImpl.saleProductsInCart(client);
    }
    public Flux<Sales> generateSalesReport(String init,String end){
        return salesImpl.salesReportInDateRange(init, end);
    }
    public Flux<TopFiveDTO> topFive(String init,String end){
        return salesImpl.topFiveReportInDateRange(init,end);
    }
}
