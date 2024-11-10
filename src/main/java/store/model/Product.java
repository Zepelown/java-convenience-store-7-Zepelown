package store.model;

import store.exception.ErrorMessage;

import java.time.LocalDateTime;
import java.util.Optional;

public class Product {
    private final String name;
    private final int price;
    private int stock;
    private final Promotion promotion;

    public Product(String name, int price, int stock, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.promotion = promotion;
    }

    public boolean canPurchase(int quantity){
        if (stock < quantity){
            throw new IllegalArgumentException(ErrorMessage.EXCEEDS_STOCK_QUANTITY.getErrorMessage());
        }
        return true;
    }

    public Optional<Integer> applyPromotion(int quantity, LocalDateTime date) {
        if (promotion != null && promotion.isPromotionApplicable(date)) {
            return promotion.calculatePromotion(quantity, this);
        }
        return Optional.empty();
    }

    public void reduceStock(int quantity) {
        if (stock < quantity){
            throw new IllegalArgumentException("");
        }
        stock -= quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
