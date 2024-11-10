package store.dto;

import store.model.Product;

import java.util.ArrayList;

public class SeparatedProducts {
    private final Product promotionProduct;
    private final ArrayList<Product> nonPromotionProducts ;

    public SeparatedProducts(Product promotionProduct, ArrayList<Product> nonPromotionProducts) {
        this.promotionProduct = promotionProduct;
        this.nonPromotionProducts = nonPromotionProducts;
    }

    public Product getPromotionProduct() {
        return promotionProduct;
    }

    public ArrayList<Product> getNonPromotionProducts() {
        return nonPromotionProducts;
    }
}
