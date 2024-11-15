package store.model;

import store.dto.InsufficientBonusProductDto;
import store.dto.PromotionQuantityOverStockDto;

import java.util.Optional;

public class Product {
    private final String name;
    private final int price;
    private final Promotion promotion;
    private int stock;

    public Product(String name, int price, int stock, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.promotion = promotion;
    }

    public boolean isPromotionActive() {
        if (promotion == null || !promotion.isPromotionApplicable()) {
            return false;
        }
        return true;
    }

    public Optional<InsufficientBonusProductDto> checkInsufficientBonusPromotionQuantity(int quantity) {
        int promotionQuantity = promotion.calculatePromotionQuantity(quantity);
        if (isPromotionActive() && promotionQuantity > quantity && promotionQuantity <= stock) {
            return Optional.of(new InsufficientBonusProductDto(name, (promotionQuantity - quantity)));

        }
        return Optional.empty();
    }

    public Optional<PromotionQuantityOverStockDto> checkPromotionQuantityOverStock(int quantity, int totalStock) {
        if (quantity > stock && totalStock >= quantity && promotion.isPromotionApplicable()) {
            return Optional.of(new PromotionQuantityOverStockDto(name, (quantity - stock)));
        }
        return Optional.empty();
    }

    public void reduceStock(int quantity) {
        if (stock >= quantity) {
            stock -= quantity;
        }
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

    @Override
    public String toString() {
        return "Product{name='" + name + "', stock=" + stock + ", price=" + price + "}";
    }
}
