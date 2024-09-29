package valko.co.cartmanagament.web.purchases.router;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import valko.co.cartmanagament.web.purchases.handler.PurchaseOrderHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class PurchaseOrderRouter {

    private final PurchaseOrderHandler purchaseOrderHandler;
    private static final String BASE_PATH = "/api/v1/buy-cart/purchase-orders";

    @Bean
    public RouterFunction<ServerResponse> purchaseOrderRoutes() {
        return route()
                .POST(BASE_PATH, purchaseOrderHandler::createPurchaseOrder)
                .GET(BASE_PATH, purchaseOrderHandler::listAllPurchaseOrders)
                .PUT(BASE_PATH.concat("/{id}"), purchaseOrderHandler::updatePurchaseOrder)
                .DELETE(BASE_PATH.concat("/{id}"), purchaseOrderHandler::deletePurchaseOrder)
                .build();
    }

}
