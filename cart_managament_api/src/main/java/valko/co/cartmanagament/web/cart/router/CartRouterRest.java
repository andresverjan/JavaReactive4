package valko.co.cartmanagament.web.cart.router;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import valko.co.cartmanagament.web.cart.handler.CartHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class CartRouterRest {

    private final CartHandler cartHandler;
    private static final String BASE_PATH = "/api/v1/buy-cart/carts";

    @Bean
    public RouterFunction<ServerResponse> cartRoutes() {
        return route()
                .GET(BASE_PATH.concat("/{userId}"), cartHandler::getCartContent)
                .GET(BASE_PATH.concat("/{userId}/total"), cartHandler::calculateTotal)
                .POST(BASE_PATH.concat("/{userId}/products"), cartHandler::addProductToCart)
                .DELETE(BASE_PATH.concat("/{userId}"), cartHandler::emptyCart)
                .DELETE(BASE_PATH.concat("/{userId}/products/{productId}"), cartHandler::removeProductFromCart)
                .build();
    }
}
