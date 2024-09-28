package com.store.shopping.drivenAdapters.salesOrder;

import com.store.shopping.DTO.TopFiveDTO;
import com.store.shopping.drivenAdapters.salesOrder.data.SalesData;
import com.store.shopping.drivenAdapters.salesOrder.data.SalesDataMapper;
import com.store.shopping.drivenAdapters.shoppingCart.ShoppingCartImpl;
import com.store.shopping.entities.ISalesRepository;
import com.store.shopping.entities.Sales;
import com.store.shopping.entities.ShoppingCart;
import com.store.shopping.entities.enums.SalesEnum;
import com.store.shopping.entities.enums.ShoppingCartEnum;
import com.store.shopping.useCase.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

@Repository
@RequiredArgsConstructor
public class SalesImpl implements ISalesRepository {
    public static final String HOUR_COMPLEMENT = " 00:00:00";
    @Autowired
    private final SalesRepository salesRepository;
    @Autowired
    private final SalesDataMapper salesDataMapper;
    @Autowired
    private final ShoppingCartImpl shoppingCart;
    @Autowired
    private final ProductService productService;
    @Override
    public Flux<Sales> saleProductsInCart(String clientId) {
        return shoppingCart.getProductsInCartByBuyer(clientId)
                .flatMap(cartItem->
                     salesRepository.save(SalesData.builder()
                            .product(cartItem.getProduct())
                            .cartItemId(cartItem.getId())
                            .state(SalesEnum.P)
                            .price(cartItem.getPrice()* cartItem.getQuantity())
                            .client(clientId)
                            .build())
                )
                .flatMap(salesData->{
                    Mono<ShoppingCart> shoppingCart1=shoppingCart.getShoppingCartItem(salesData.getCartItemId())
                            .flatMap(item ->{
                                productService.sellProduct(item.getProduct(), item.getQuantity());
                                item.setStatus(ShoppingCartEnum.S);
                               return shoppingCart.updateItem(item.getId()
                                       , item);
                            });
                    shoppingCart1.subscribe(System.out::println);
                    salesData.setState(SalesEnum.S);
                    return salesRepository.save(salesData);
                })
                .map(salesDataMapper::toModel)
                .onErrorResume(e -> Mono.error(new CustomServiceException("Error en la venta de productos "+e)));
    }

    @Override
    public Flux<Sales> salesReportInDateRange(String initDate, String endDate) {
        Timestamp init = Timestamp.valueOf(initDate + HOUR_COMPLEMENT);
        Timestamp end = Timestamp.valueOf(endDate + HOUR_COMPLEMENT);
        return salesRepository.getSalesInRange(init, end)
                .map(salesDataMapper::toModel);
    }

    @Override
    public Flux<TopFiveDTO> topFiveReportInDateRange(String initDate, String endDate) {
        Timestamp init = Timestamp.valueOf(initDate + HOUR_COMPLEMENT);
        Timestamp end = Timestamp.valueOf(endDate + HOUR_COMPLEMENT);
        return salesRepository.getTopFive(init, end);
    }
    public static class CustomServiceException extends RuntimeException {
        public CustomServiceException(String message) {
            super(message);
        }
    }
}
