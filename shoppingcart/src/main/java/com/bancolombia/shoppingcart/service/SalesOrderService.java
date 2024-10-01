package com.bancolombia.shoppingcart.service;

import com.bancolombia.shoppingcart.dto.SalesOrderDTO;
import com.bancolombia.shoppingcart.dto.SalesOrderDetailDTO;
import com.bancolombia.shoppingcart.entity.Cart;
import com.bancolombia.shoppingcart.entity.SalesOrder;
import com.bancolombia.shoppingcart.entity.SalesOrderDetail;
import com.bancolombia.shoppingcart.repository.SalesOrderDetailRepository;
import com.bancolombia.shoppingcart.repository.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalesOrderService {
    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private SalesOrderDetailRepository salesOrderDetailRepository;

    public Mono<SalesOrderDTO> createSalesOrderOfCart(Cart cart){

        List<SalesOrderDetail> salesOrderDetail = new ArrayList<>();
        cart.getCartDetail().forEach(cartDetail -> salesOrderDetail.add(
                SalesOrderDetail.builder()
                        .amount(cartDetail.getAmount())
                        .salesPrice(cartDetail.getPrice())
                        .amountdiscount(cartDetail.getAmountdiscount())
                        .amounttax(cartDetail.getAmounttax())
                        .productid(cartDetail.getProductid())
                        .build()
        ));

        SalesOrder salesOrder = SalesOrder.builder()
                .userid(cart.getUserid())
                .iscancelled(false)
                .subtotalorder(cart.getSubtotalorder())
                .totaldiscount(cart.getTotaldiscount())
                .totaltax(cart.getTotaltax())
                .salesOrderDetails(salesOrderDetail)
                .build();

        return salesOrderRepository.save(salesOrder).flatMap(newSalesOrder -> {
                    newSalesOrder.getSalesOrderDetails().forEach(
                            cartDetail -> salesOrderDetailRepository.save(cartDetail));
                    return null;
                });
    }
}

