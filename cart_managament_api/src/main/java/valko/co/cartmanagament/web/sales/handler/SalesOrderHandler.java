package valko.co.cartmanagament.web.sales.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import valko.co.cartmanagament.model.sale.SaleOrder;
import valko.co.cartmanagament.service.SaleOrderService;

import java.util.logging.Level;

@Log
@Component
@RequiredArgsConstructor
public class SalesOrderHandler {

    private final SaleOrderService salesOrderService;
    public static final String MESSAGE_LOG = "Message: {0}";

    public Mono<ServerResponse> createSalesOrder(ServerRequest serverRequest) {
        var userId = Integer.parseInt(serverRequest.pathVariable("userId"));
        return salesOrderService.createOrder(userId)
                .flatMap(order -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(order))
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Sales order created successfully"}));
    }

    public Mono<ServerResponse> deleteSalesOrder(ServerRequest serverRequest) {
        var orderId = Integer.parseInt(serverRequest.pathVariable("id"));
        return salesOrderService.deleteOrder(orderId)
                .then(ServerResponse.noContent().build())
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Sales order cancelled successfully"}));
    }

    public Mono<ServerResponse> listAllSalesOrders(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(salesOrderService.listAllOrders(), SaleOrder.class)
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Sales orders retrieved successfully"}));
    }

    public Mono<ServerResponse> listSalesOrdersByProductId(ServerRequest serverRequest) {
        var productId = Integer.parseInt(serverRequest.pathVariable("productId"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(salesOrderService.listOrdersByProductId(productId), SaleOrder.class)
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Sales orders retrieved successfully for product ID: " + productId}));
    }

}
