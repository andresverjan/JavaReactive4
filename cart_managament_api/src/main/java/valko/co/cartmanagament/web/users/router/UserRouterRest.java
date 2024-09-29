package valko.co.cartmanagament.web.users.router;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import valko.co.cartmanagament.web.users.handler.UserHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class UserRouterRest {

    private final UserHandler userHandler;
    private static final String BASE_PATH = "/api/v1/buy-cart/users";

    @Bean
    public RouterFunction<ServerResponse> userRoutes() {
        return route()
                .POST(BASE_PATH, userHandler::createUser)
                .GET(BASE_PATH.concat("/{id}"), userHandler::findUserById)
                .GET(BASE_PATH.concat("/search"), userHandler::findUserByEmail)
                .PUT(BASE_PATH.concat("/{id}"), userHandler::updateUser)
                .DELETE(BASE_PATH.concat("/{id}"), userHandler::deleteUserById)
                .build();
    }
}
