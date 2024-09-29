package valko.co.cartmanagament.web.reports.router;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import valko.co.cartmanagament.web.reports.handler.ReportHandle;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class ReportRouterRest {

    private final ReportHandle reportHandler;
    private static final String BASE_PATH = "/api/v1/buy-cart/reports";

    @Bean
    public RouterFunction<ServerResponse> reportRoutes() {
        return route()
                .GET(BASE_PATH.concat("/sales"), reportHandler::generateSalesReport)
                .GET(BASE_PATH.concat("/sales/top5"), reportHandler::generateTop5SalesReport)
                .build();
    }
}
