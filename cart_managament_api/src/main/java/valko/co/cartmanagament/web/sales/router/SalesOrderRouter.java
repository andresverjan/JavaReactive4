package valko.co.cartmanagament.web.sales.router;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import valko.co.cartmanagament.web.sales.handler.SalesOrderHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class SalesOrderRouter {

    private final SalesOrderHandler salesOrderHandler;
    private static final String BASE_PATH = "/api/v1/buy-cart/sales-orders";

    @Bean
    public RouterFunction<ServerResponse> salesOrderRoutes() {
        return route()
                .POST(BASE_PATH.concat("/{userId}"), salesOrderHandler::createSalesOrder)
                .DELETE(BASE_PATH.concat("/{id}"), salesOrderHandler::deleteSalesOrder)
                .GET(BASE_PATH, salesOrderHandler::listAllSalesOrders)
                .GET(BASE_PATH.concat("/product/{productId}"), salesOrderHandler::listSalesOrdersByProductId)
                .build();
    }

}
