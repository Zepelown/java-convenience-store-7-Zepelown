package store.service;

import store.data.entity.Product;
import store.data.repository.StoreProductRepository;
import store.data.repository.StorePromotionRepository;
import store.model.PurchaseProduct;

import java.util.List;

public class StoreBuyingService {
    private final StoreProductRepository storeProductRepository;
    private final StorePromotionRepository storePromotionRepository;

    public StoreBuyingService(StoreProductRepository storeProductRepository, StorePromotionRepository storePromotionRepository) {
        this.storeProductRepository = storeProductRepository;
        this.storePromotionRepository = storePromotionRepository;
    }

    public void buyProducts(List<PurchaseProduct> purchaseProducts) {
        for (PurchaseProduct purchaseProduct : purchaseProducts) {
            Product productToBuy = null;
            List<Product> purchasableProducts = storeProductRepository.findPurchasableProducts(purchaseProduct);
            for (Product purchasableProduct : purchasableProducts) {
                if (productToBuy != null && !productToBuy.hasPromotion()){
                    continue;
                }
                productToBuy = purchasableProduct;
            }
        }

    }
}
