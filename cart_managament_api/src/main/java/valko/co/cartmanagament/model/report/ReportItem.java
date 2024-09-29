package valko.co.cartmanagament.model.report;

import lombok.Builder;

@Builder
public record ReportItem(
        Integer orderId,
        Integer productId,
        double totalAmount) {

    public ReportItem(Integer orderId, Integer productId, double totalAmount) {
        this.orderId = orderId;
        this.productId = productId;
        this.totalAmount = totalAmount;
    }
}
