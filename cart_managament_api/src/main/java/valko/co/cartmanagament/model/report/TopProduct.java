package valko.co.cartmanagament.model.report;

public record TopProduct(
        Integer productId,
        Double totalAmount) {

    public TopProduct(Integer productId, Double totalAmount) {
        this.productId = productId;
        this.totalAmount = totalAmount;
    }
}
