package valko.co.cartmanagament.web.reports.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import valko.co.cartmanagament.service.ReportService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;

@Log
@Component
@RequiredArgsConstructor
public class ReportHandle {

    public static final String MESSAGE_LOG = "Message: {0}";
    private final ReportService reportService;

    public Mono<ServerResponse> generatePurchaseReport(ServerRequest serverRequest) {
        LocalDateTime startDate = parseDate(serverRequest.queryParam("startDate").orElse(""));
        LocalDateTime endDate = parseDate(serverRequest.queryParam("endDate").orElse(""));

        return reportService.generatePurchaseReport(startDate, endDate)
                .flatMap(report -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(report))
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Purchase report generated successfully"}));
    }

//    public Mono<ServerResponse> generateSalesReport(ServerRequest serverRequest) {
//        LocalDateTime startDate = parseDate(serverRequest.queryParam("startDate").orElse(""));
//        LocalDateTime endDate = parseDate(serverRequest.queryParam("endDate").orElse(""));
//
//        return reportService.generateSalesReport(startDate, endDate)
//                .flatMap(report -> ServerResponse.ok()
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .bodyValue(report))
//                .doOnSuccess(response -> log.log(Level.INFO,
//                        MESSAGE_LOG,
//                        new String[]{"Sales report generated successfully"}));
//    }

//    public Mono<ServerResponse> generateTop5SalesReport(ServerRequest serverRequest) {
//        LocalDateTime startDate = parseDate(serverRequest.queryParam("startDate").orElse(""));
//        LocalDateTime endDate = parseDate(serverRequest.queryParam("endDate").orElse(""));
//
//        return reportService.generateTop5SalesReport(startDate, endDate)
//                .flatMap(report -> ServerResponse.ok()
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .bodyValue(report))
//                .doOnSuccess(response -> log.log(Level.INFO,
//                        MESSAGE_LOG,
//                        new String[]{"Top 5 sales report generated successfully"}));
//    }

    private LocalDateTime parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return date.isEmpty() ? null : LocalDateTime.parse(date, formatter);
    }

}
