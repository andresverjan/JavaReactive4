package valko.co.cartmanagament.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import valko.co.cartmanagament.model.report.Report;
import valko.co.cartmanagament.model.report.Report5;
import valko.co.cartmanagament.model.report.ReportItem;

import valko.co.cartmanagament.model.report.ReportTop5;
import valko.co.cartmanagament.repository.product.ProductRepository;
import valko.co.cartmanagament.repository.sales.SaleOrderDetailRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ProductRepository productRepository;
    private final SaleOrderDetailRepository saleOrderDetailRepository;

    public Mono<Report> generateSalesReport(LocalDateTime startDate, LocalDateTime endDate) {
        return saleOrderDetailRepository.findOrderDetailsByDateRange(startDate, endDate)
                .collectList()
                .map(saleOrderDetails -> {

                    List<ReportItem> reportItems = saleOrderDetails.stream()
                            .map(saleOrderDetail -> new ReportItem(
                                    saleOrderDetail.saleOrderId(),
                                    saleOrderDetail.productId(),
                                    saleOrderDetail.amount()))
                            .collect(Collectors.toList());

                    return new Report("Sales Report", startDate, endDate, reportItems);
                });
    }

    public Mono<Report5> generateTop5SalesReport(LocalDateTime startDate, LocalDateTime endDate) {
        return saleOrderDetailRepository.findTop5ProductsByDateRange(startDate, endDate)
                .flatMap(topProduct -> productRepository.findById(topProduct.productId())
                        .map(product -> new ReportTop5(product.name(), product.price(), topProduct.totalAmount())))
                .collectList()
                .map(items -> new Report5("Top 5 Sold Products", startDate, endDate, items));
    }

}
