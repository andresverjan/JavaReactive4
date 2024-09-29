package valko.co.cartmanagament.model.report;

import lombok.Builder;

@Builder(toBuilder = true)
public record ReportTop5(
        String name,
        double price,
        double totalAmount) {

    public ReportTop5(String name, double price, double totalAmount) {
        this.name = name;
        this.price = price;
        this.totalAmount = totalAmount;
    }
}
