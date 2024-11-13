package store.model.receipt;

public class TotalCostPerProduct {
    int quantity;
    int totalCost;

    public TotalCostPerProduct(int quantity, int totalCost) {
        this.quantity = quantity;
        this.totalCost = totalCost;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalCost() {
        return totalCost;
    }
}