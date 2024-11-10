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

    public boolean isPromotionActive(){
        if (promotion == null || !promotion.isPromotionApplicable()){
            return false;
        }
        return true;
    }

    public void checkInsufficientBonusPromotionQuantity(int quantity){
        int promotionQuantity = promotion.calculatePromotionQuantity(quantity);
        if (promotionQuantity > quantity){
            throw new IllegalArgumentException("현재 "+name+"은(는) "+(promotionQuantity-quantity)+"개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
        }
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
