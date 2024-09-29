package valko.co.cartmanagament.web.product.router;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import valko.co.cartmanagament.web.product.handler.ProductHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class ProductRouterRest {

    private final ProductHandler productHandler;
    private static final String BASE_PATH = "/api/v1/buy-cart/products";

    @Bean
    public RouterFunction<ServerResponse> routerPerson() {
        return route()
                .GET(BASE_PATH, productHandler::listAllProducts)
                .GET(BASE_PATH.concat("/{id}"), productHandler::findProductById)
                .GET(BASE_PATH.concat("/{name}"), productHandler::findProductByName)
                .POST(BASE_PATH, productHandler::saveProduct)
                .PUT(BASE_PATH.concat("/{id}"), productHandler::updateProduct)
                .PATCH(BASE_PATH.concat("/{id}/stock"), productHandler::updateProductStock)
                .DELETE(BASE_PATH.concat("/{id}"), productHandler::deleteProductById)
                .build();
    }
}
