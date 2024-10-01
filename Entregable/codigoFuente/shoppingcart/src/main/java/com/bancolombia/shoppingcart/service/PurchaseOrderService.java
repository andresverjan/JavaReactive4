package com.bancolombia.shoppingcart.service;

import com.bancolombia.shoppingcart.repository.PurchaseOrderDetailRepository;
import com.bancolombia.shoppingcart.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderService {
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private PurchaseOrderDetailRepository purchaseOrderDetailRepository;
}
