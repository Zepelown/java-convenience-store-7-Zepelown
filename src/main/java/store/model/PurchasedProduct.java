package store.model;

public class PurchasedProduct {
    private final String name;
    private final int price;
    private Promotion promotion;
    private int totalQuantity;
    private int buyingQuantity;
    private int freeQuantity;

    public PurchasedProduct(String name, int totalQuantity, int buyingQuantity, int freeQuantity, int price, Promotion promotion) {
        this.name = name;
        this.totalQuantity = totalQuantity;
        this.buyingQuantity = buyingQuantity;
        this.freeQuantity = freeQuantity;
        this.price = price;
        this.promotion = promotion;
        if (this.promotion == null) {
            this.promotion = new Promotion("null", 0, 0, null, null);
        }
        calculateTotalQuantities();
    }

    public void calculateTotalQuantities() {
        if (promotion != null && promotion.isPromotionApplicable()) {
            int[] quantities = promotion.calculatePaidAndFreeQuantity(totalQuantity);
            this.buyingQuantity = quantities[0];
            this.freeQuantity = quantities[1];
        } else {
            this.buyingQuantity = totalQuantity;
            this.freeQuantity = 0;
        }
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void addFreeQuantity(int quantity) {
        totalQuantity += quantity;
        freeQuantity += quantity;
    }

    public void minusBuyingQuantity(int quantity){
        totalQuantity -= quantity;
        buyingQuantity -= quantity;
    }

    public void minusTotalQuantity(int quantity) {
        totalQuantity -= quantity;
        calculateTotalQuantities();
    }

    public String getName() {
        return name;
    }

    public int getBuyingQuantity() {
        return buyingQuantity;
    }

    public int getPrice() {
        return price;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public int getFreeQuantity() {
        return freeQuantity;
    }

    @Override
    public String toString() {
        return "PurchasedProduct{name='" + name + "', quantity=" + buyingQuantity + ", price=" + price + "}";
    }
}
