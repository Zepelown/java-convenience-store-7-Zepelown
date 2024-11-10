package store.model;

import store.dto.InsufficientBonusProductDto;
import store.dto.PromotionQuantityOverStockDto;

import java.util.ArrayList;
import java.util.Optional;

public class PromotionProductGroup {
    private final Product promotionProduct;
    private final ArrayList<Product> nonPromotionProducts;
    private final int totalStock;
    public PromotionProductGroup(Product promotionProduct, ArrayList<Product> nonPromotionProducts, int totalStock) {
        this.promotionProduct = promotionProduct;
        this.nonPromotionProducts = nonPromotionProducts;
        this.totalStock = totalStock;
    }

    public InsufficientBonusProductDto checkInsufficientBonusPromotionQuantity(PurchaseProduct purchaseProduct) {
        if (promotionProduct == null) {
            return null;
        }

        Optional<InsufficientBonusProductDto> insufficientBonusProductDto = promotionProduct.checkInsufficientBonusPromotionQuantity(purchaseProduct.getQuantity());

        return insufficientBonusProductDto.orElse(null);

    }

    public PromotionQuantityOverStockDto checkPromotionQuantityOverStock(PurchaseProduct purchaseProduct) {
        if (promotionProduct == null) {
            return null;
        }
        Optional<PromotionQuantityOverStockDto> promotionQuantityOverStockDto = promotionProduct.checkPromotionQuantityOverStock(purchaseProduct.getQuantity(), totalStock);

        return promotionQuantityOverStockDto.orElse(null);
    }

    public Product getPromotionProduct() {
        return promotionProduct;
    }

    public ArrayList<Product> getNonPromotionProducts() {
        return nonPromotionProducts;
    }

    public int getProductCost(){return nonPromotionProducts.getFirst().getPrice();}

    public Promotion getProductPromotion() {return promotionProduct.getPromotion();}

    public int getTotalStock() {
        return totalStock;
    }


}
