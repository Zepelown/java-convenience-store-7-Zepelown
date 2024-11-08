package store.data.entity;

import store.dto.ProductDto;
import store.util.EntityValidator;

import java.io.IOException;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private String promotion;
    public Product(String name, String price, String quantity, String promotion) {
        this.name = name;
        this.price = EntityValidator.parseInt(price);
        this.quantity = EntityValidator.parseInt(quantity);
        this.promotion = promotion;
        validate();
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }

    public void validate()  {
        EntityValidator.validateString(name);
        EntityValidator.validateInt(price);
        EntityValidator.validateInt(quantity);
        EntityValidator.validateString(promotion);
    }

    public ProductDto toDto(){
        return new ProductDto(name,price,quantity,promotion);
    }
}
