package store.service;

import store.data.entity.ProductEntity;
import store.data.repository.StoreProductRepository;
import store.data.repository.StorePromotionRepository;
import store.dto.ProductDto;
import store.model.Product;
import store.model.Promotion;
import store.model.PurchaseProduct;

import java.util.*;
import java.util.stream.Collectors;

public class StoreStockService {
    private final StoreProductRepository storeProductRepository;
    private final StorePromotionRepository storePromotionRepository;
    private final HashMap<String, List<Product>> stock = new HashMap<>();

    public StoreStockService(StoreProductRepository storeProductRepository, StorePromotionRepository storePromotionRepository) {
        this.storeProductRepository = storeProductRepository;
        this.storePromotionRepository = storePromotionRepository;
        initStockSetting();
    }

    public List<ProductDto> loadProductStock() {
        return storeProductRepository.loadProductStock().stream()
                .map(ProductEntity::toDto)
                .collect(Collectors.toList());
    }

    public List<Product> getPurchasableProducts(List<PurchaseProduct> purchaseProducts) {
        List<Product> purchasableProducts = new ArrayList<>();

        purchaseProducts.forEach(purchaseProduct ->
                purchasableProducts.addAll(
                        stock.getOrDefault(purchaseProduct.getName(), new ArrayList<>())
                )
        );
        return purchasableProducts;
    }

    private void initStockSetting() {
        List<ProductEntity> productEntities = storeProductRepository.loadProductStock();
        for (ProductEntity productEntity : productEntities) {
            Promotion promotion = storePromotionRepository.loadPromotion(productEntity.getPromotion())
                    .map(promotionEntity -> promotionEntity.toPromotion())
                    .orElseGet(() -> new Promotion("null", 0, 0, null, null));

            stock.computeIfAbsent(productEntity.getName(), name -> new ArrayList<>())
                    .add(productEntity.toProduct(promotion));
        }
    }
}
