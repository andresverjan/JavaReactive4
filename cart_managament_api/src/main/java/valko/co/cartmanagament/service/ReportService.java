package valko.co.cartmanagament.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import valko.co.cartmanagament.model.buy.PurchaseOrder;
import valko.co.cartmanagament.model.products.Product;
import valko.co.cartmanagament.model.report.Report;
import valko.co.cartmanagament.model.report.ReportItem;
import valko.co.cartmanagament.model.sale.SaleOrder;
import valko.co.cartmanagament.repository.purchaseorder.PurchaseOrderRepository;
import valko.co.cartmanagament.repository.sales.SaleOrderRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SaleOrderRepository saleOrderRepository;

    public Mono<Report> generatePurchaseReport(LocalDateTime startDate, LocalDateTime endDate) {
        return purchaseOrderRepository.findAll()
                .filter(order -> isWithinDateRange(order.creationDate(), startDate, endDate))
                .flatMap(this::toReportItem)
                .collectList()
                .map(items -> new Report("Purchase Report", startDate, endDate, items));
    }

//    public Mono<Report> generateSalesReport(LocalDateTime startDate, LocalDateTime endDate) {
//        return saleOrderRepository.findAll()
//                .filter(order -> isWithinDateRange(order.creationDate(), startDate, endDate))
//                .flatMap(this::toReportItem)
//                .collectList()
//                .map(items -> new Report("Sales Report", startDate, endDate, items));
//    }

//    public Mono<Report> generateTop5SalesReport(LocalDateTime startDate, LocalDateTime endDate) {
//        return saleOrderRepository.findAll()
//                .filter(order -> isWithinDateRange(order.creationDate(), startDate, endDate))
//                .flatMapIterable(SaleOrder::userId)
//                .groupBy(Product::id)
//                .flatMap(group -> group.collectList()
//                        .map(products -> new ReportItem(products.get(0).name(), products.size())))
//                .sort((item1, item2) -> Integer.compare(item2.quantity(), item1.quantity()))
//                .take(5)
//                .collectList()
//                .map(items -> new Report("Top 5 Sold Products", startDate, endDate, items));
//    }

    private boolean isWithinDateRange(LocalDateTime date,
                                      LocalDateTime startDate,
                                      LocalDateTime endDate) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    private Mono<ReportItem> toReportItem(PurchaseOrder order) {
        return Mono.just(new ReportItem("Purchase Order #" + order.id(),
                order.products().size(),
                order.total()));
    }

//    private Mono<ReportItem> toReportItem(SaleOrder order) {
//        return Mono.just(new ReportItem("Sale Order #" + order.id(),
//                order.products().size(),
//                order.total()));
//    }

}
