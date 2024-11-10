package store.dto;

public class InsufficientBonusProductDto {
    private final String name;
    private final int quantity;

    public InsufficientBonusProductDto(String name, int quantity) {
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
