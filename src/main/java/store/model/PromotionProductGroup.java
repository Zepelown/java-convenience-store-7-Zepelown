package store.model;

import store.dto.InsufficientBonusProductDto;
import store.dto.PromotionQuantityOverStockDto;

import java.util.Optional;

public class PromotionProductGroup {
    private  Product promotionProduct;
    private final Product nonPromotionProduct;
    private final int totalStock;
    public PromotionProductGroup(Product promotionProduct, Product nonPromotionProduct, int totalStock) {
        this.promotionProduct = promotionProduct;
        if (this.promotionProduct == null){
            this.promotionProduct = new Product("null",0,0, new Promotion("null", 0, 0, null, null));
        }
        this.nonPromotionProduct = nonPromotionProduct;
        this.totalStock = totalStock;
    }

    public InsufficientBonusProductDto checkInsufficientBonusPromotionQuantity(PurchasedProduct purchaseProduct) {
        if (promotionProduct == null) {
            return null;
        }

        Optional<InsufficientBonusProductDto> insufficientBonusProductDto = promotionProduct.checkInsufficientBonusPromotionQuantity(purchaseProduct.getTotalQuantity());

        return insufficientBonusProductDto.orElse(null);

    }

    public PromotionQuantityOverStockDto checkPromotionQuantityOverStock(PurchasedProduct purchaseProduct) {
        if (promotionProduct == null) {
            return null;
        }
        Optional<PromotionQuantityOverStockDto> promotionQuantityOverStockDto = promotionProduct.checkPromotionQuantityOverStock(purchaseProduct.getTotalQuantity(), totalStock);

        return promotionQuantityOverStockDto.orElse(null);
    }

    public Product getPromotionProduct() {
        return promotionProduct;
    }
    public int getProductCost(){return nonPromotionProduct.getPrice();}

    public Promotion getProductPromotion() {
        if (promotionProduct == null){
            return null;
        }
        return promotionProduct.getPromotion();
    }

    public int getTotalStock() {
        return totalStock;
    }


}
