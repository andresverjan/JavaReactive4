package valko.co.cartmanagament.web.purchases.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import valko.co.cartmanagament.model.buy.PurchaseOrder;
import valko.co.cartmanagament.service.PurchaseOrderService;

import java.util.List;
import java.util.logging.Level;

@Log
@Component
@RequiredArgsConstructor
public class PurchaseOrderHandler {

    private final PurchaseOrderService purchaseOrderService;
    private static final String MESSAGE_LOG = "Message: {0}";

    public Mono<ServerResponse> createPurchaseOrder(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(List.class)
                .flatMap(productIds -> purchaseOrderService.createPurchaseOrder((List<Integer>) productIds))
                .flatMap(order -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(order))
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Purchase order created successfully"}));
    }

    public Mono<ServerResponse> updatePurchaseOrder(ServerRequest serverRequest) {
        var orderId = Integer.parseInt(serverRequest.pathVariable("id"));
        return serverRequest.bodyToMono(List.class)
                .flatMap(productIds -> purchaseOrderService.updatePurchaseOrder(orderId, (List<Integer>) productIds))
                .flatMap(order -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(order))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Purchase order updated successfully"}));
    }

    public Mono<ServerResponse> deletePurchaseOrder(ServerRequest serverRequest) {
        var orderId = Integer.parseInt(serverRequest.pathVariable("id"));
        return purchaseOrderService.cancelPurchaseOrder(orderId)
                .then(ServerResponse.noContent().build())
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Purchase order cancelled successfully"}));
    }

    public Mono<ServerResponse> listAllPurchaseOrders(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(purchaseOrderService.listAllPurchaseOrders(), PurchaseOrder.class)
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Purchase orders retrieved successfully"}));
    }

}
