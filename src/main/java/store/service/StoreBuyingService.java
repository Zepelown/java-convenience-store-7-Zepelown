package store.service;

import store.data.entity.Product;
import store.data.repository.StoreProductRepository;
import store.data.repository.StorePromotionRepository;
import store.exception.ErrorMessage;
import store.model.PurchaseProduct;

import java.util.ArrayList;
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
            List<Product> purchasableProducts = getPurchasableProducts(purchaseProduct);

        }
    }

    private List<Product> getPurchasableProducts(PurchaseProduct purchaseProduct) {
        List<Product> purchasableProducts = new ArrayList<>();
        List<Product> purchasableProductsByName = storeProductRepository.findPurchasableProductsByName(purchaseProduct.getName())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NON_EXISTENT_PRODUCT.getErrorMessage()));
        for (Product purchasableProduct : purchasableProductsByName) {
            if (purchasableProduct.isStockAvailable(purchaseProduct.getQuantity())) {
                purchasableProducts.add(purchasableProduct);
            }
        }

        if (purchasableProducts.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.EXCEEDS_STOCK_QUANTITY.getErrorMessage());
        }
        return purchasableProducts;
    }
}
