package store.dto;

public class PromotionQuantityOverStockDto {
    private final String name;
    private final int quantity;

    public PromotionQuantityOverStockDto(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
