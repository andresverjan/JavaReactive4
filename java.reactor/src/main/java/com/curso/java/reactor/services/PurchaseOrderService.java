package com.curso.java.reactor.services;

import com.curso.java.reactor.models.PurchaseOrder;
import com.curso.java.reactor.models.PurchaseOrderProduct;
import com.curso.java.reactor.models.dto.PurchaseOrderDTO;
import com.curso.java.reactor.models.dto.ProductQuantityDTO;
import com.curso.java.reactor.repository.PurchaseOrderProductRepository;
import com.curso.java.reactor.repository.PurchaseOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PurchaseOrderService {
    private  final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderProductRepository purchaseOrderProductRepository;
    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderService.class);


    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository,
                           PurchaseOrderProductRepository purchaseOrderProductRepository,
                           ProductService productService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderProductRepository = purchaseOrderProductRepository;
        this.productService = productService;
    }

    public Flux<PurchaseOrderDTO>listarOrdenesComra(){
        return purchaseOrderRepository.findAll()
                .flatMap(purchaseOrder -> obtenerDetalleCompras((long) purchaseOrder.getId())
                        .map(detalleCompra -> PurchaseOrderDTO.builder()
                                .purchaseId((long) purchaseOrder.getId())
                                .state(purchaseOrder.getState())
                                .items(detalleCompra.getItems())
                                .total(purchaseOrder.getTotal())
                                .build()
                        )
                );
    }

    public Flux<PurchaseOrderDTO> getPurchaseOrdersBetweenDates(String initDate, String endDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(initDate, formatter);
        LocalDateTime endDateFormated = LocalDateTime.parse(endDate, formatter);
        return purchaseOrderRepository.findAllByUpdatedAtBetween(startDate, endDateFormated)
                .flatMap(purchaseOrder -> obtenerDetalleCompras((long) purchaseOrder.getId())
                        .map(detalleCompra -> PurchaseOrderDTO.builder()
                                .purchaseId((long) purchaseOrder.getId())
                                .state(purchaseOrder.getState())
                                .items(detalleCompra.getItems())
                                .total(purchaseOrder.getTotal())
                                .build()
                        )
                );
    }







    public Mono<PurchaseOrderDTO> registrarOrdenCompra(List<ProductQuantityDTO> productos, Double total) {
        logger.info("Registrando orden de compra con productos: {} y total: {}", productos, total);
        return crearOrdenCompra(total)
                .doOnNext(purchaseOrder -> logger.info("Orden de compra creada: {}", purchaseOrder))
                .flatMap(purchaseOrder ->
                        crearOrdenCompraProducto(productos, purchaseOrder.getId())
                                .collectList()
                                .doOnNext(ordenCompraProductos -> logger.info("Productos de la orden de compra creados: {}", ordenCompraProductos))
                                .flatMap(ordenCompraProductos ->
                                        actualizarStockProductos(productos).then(
                                                productService.mapItemsPurchased(productos)
                                                        .doOnNext(response -> logger.info("Items mapeados: {}", response))
                                                        .map(response -> PurchaseOrderDTO.builder()
                                                                .purchaseId((long) purchaseOrder.getId())
                                                                .items(response.getItems())
                                                                .state(purchaseOrder.getState())
                                                                .total(purchaseOrder.getTotal())
                                                                .build()
                                                        )
                                        )
                                )
                );
    }

    private Mono<PurchaseOrder> crearOrdenCompra(Double total){
        PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                .total(total)
                .state("RECIBIDA")
                .build();
        return purchaseOrderRepository.save(purchaseOrder)
                .doOnSuccess(savedPurchaseOrder -> logger.info("Orden de compra guardada en la base de datos: {}", savedPurchaseOrder))
                .doOnError(error -> logger.error("Error al guardar la orden de compra: ", error));
    }

    private Flux<PurchaseOrderProduct> crearOrdenCompraProducto(List<ProductQuantityDTO> productos, int ordenCompraId){
        return Flux.fromIterable(productos)
                .flatMap(producto -> {
                    PurchaseOrderProduct purchaseOrderProduct = PurchaseOrderProduct.builder()
                            .purchaseOrderId(ordenCompraId)
                            .productId(producto.getProductId())
                            .quantity(producto.getQuantity())
                            .build();
                    return purchaseOrderProductRepository.save(purchaseOrderProduct);
                });
    }

    public Mono<Void> actualizarStockProductos(List<ProductQuantityDTO> productos) {
    return Flux.fromIterable(productos)
            .flatMap(producto -> productService
                    .findProductStock((long)producto.getProductId(), -producto.getQuantity())
                    .flatMap(productoStock -> {
                        int nuevoStock = productoStock.getStock() + producto.getQuantity();
                        return productService.updateStock((long) productoStock.getId(), nuevoStock);
                    })
            )
            .then();
}

    public Mono<PurchaseOrderDTO> obtenerDetalleCompras(Long ordenCompraId){
        return purchaseOrderProductRepository.findByPurchaseOrderId(ordenCompraId)
                .flatMap(purchaseOrderProduct ->
                        productService.findProduct((long) purchaseOrderProduct.getProductId())
                        .map(producto -> ProductQuantityDTO.builder()
                                .productId(producto.getId())
                                .quantity(purchaseOrderProduct.getQuantity())
                                .build())
                )
                .collectList()
                .flatMap(productService::mapItemsPurchased);
    }




}
