package valko.co.cartmanagament.model.report;

import lombok.Builder;

@Builder
public record ReportItem(
        String itemName,
        int quantity,
        double totalAmount) {

    public ReportItem(String itemName, int quantity) {
        this(itemName, quantity, 0);
    }
}
