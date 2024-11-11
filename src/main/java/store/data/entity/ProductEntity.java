package store.data.entity;

import store.model.Product;
import store.model.Promotion;
import store.util.EntityValidator;

public class ProductEntity {
    private String name;
    private int price;
    private int quantity;
    private String promotion;

    public ProductEntity(String name, String price, String quantity, String promotion) {
        this.name = name;
        this.price = EntityValidator.parseInt(price);
        this.quantity = EntityValidator.parseInt(quantity);
        this.promotion = promotion;
        validate();
    }

    public String getName() {
        return name;
    }


    public int getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }

    public Product toProduct(Promotion promotion) {
        return new Product(name, price, quantity, promotion);
    }

    private void validate() {
        EntityValidator.validateString(name);
        EntityValidator.validateInt(price);
        EntityValidator.validateInt(quantity);
        EntityValidator.validateString(promotion);
    }
}
