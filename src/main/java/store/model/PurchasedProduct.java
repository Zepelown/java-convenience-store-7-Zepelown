package store.model;

public class PurchasedProduct {
    private final String name;
    private int promotionQuantity;
    private int nonPromotionQuantity;
    private final int price;
    private final Promotion promotion;

    public PurchasedProduct(String name, int promotionQuantity, int nonPromotionQuantity, int price, Promotion promotion) {
        this.name = name;
        this.promotionQuantity = promotionQuantity;
        this.nonPromotionQuantity = nonPromotionQuantity;
        this.price = price;
        this.promotion = promotion;
    }

    public void addPromotionQuantity(int quantity){
        promotionQuantity += quantity;
    }
    public void addNonPromotionQuantity(int quantity){
        nonPromotionQuantity += quantity;
    }

    public String getName() {
        return name;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public int getPrice() {
        return price;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public int getNonPromotionQuantity() {
        return nonPromotionQuantity;
    }

    @Override
    public String toString() {
        return "PurchasedProduct{name='" + name + "', quantity=" + promotionQuantity + ", price=" + price + "}";
    }
}
