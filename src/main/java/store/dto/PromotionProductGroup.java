package store.dto;

import store.model.Product;

import java.util.ArrayList;

public class PromotionProductGroup {
    private final Product promotionProduct;
    private final ArrayList<Product> nonPromotionProducts;

    private final int totalStock;

    public PromotionProductGroup(Product promotionProduct, ArrayList<Product> nonPromotionProducts, int totalStock) {
        this.promotionProduct = promotionProduct;
        this.nonPromotionProducts = nonPromotionProducts;
        this.totalStock = totalStock;
    }

    public Product getPromotionProduct() {
        return promotionProduct;
    }

    public ArrayList<Product> getNonPromotionProducts() {
        return nonPromotionProducts;
    }

    public int getTotalStock() {
        return totalStock;
    }
}
