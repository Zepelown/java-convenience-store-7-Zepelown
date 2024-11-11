package store.service;

import store.data.entity.ProductEntity;
import store.data.repository.StoreProductRepository;
import store.data.repository.StorePromotionRepository;
import store.exception.ErrorMessage;
import store.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StoreStockService {
    private final StoreProductRepository storeProductRepository;
    private final StorePromotionRepository storePromotionRepository;
    private final HashMap<String, List<Product>> stock = new HashMap<>();

    public StoreStockService(StoreProductRepository storeProductRepository, StorePromotionRepository storePromotionRepository) {
        this.storeProductRepository = storeProductRepository;
        this.storePromotionRepository = storePromotionRepository;
        initStockSetting();
    }

    public HashMap<String, List<Product>> loadProductStock() {
        return stock;
    }

    public PurchasedProduct buyProduct(PurchasedProduct purchasedProduct, PromotionProductGroup promotionProductGroup) {
        int promotionStock = promotionProductGroup.getPromotionProduct().getStock();
        int requiredStock = purchasedProduct.getTotalQuantity();


        if (requiredStock <= promotionStock) {
            reducePromotionStock(purchasedProduct.getName(), requiredStock);
            return purchasedProduct;
        }
        reducePromotionStock(purchasedProduct.getName(), promotionStock);
        int remainingBuyingStock = purchasedProduct.getTotalQuantity() - promotionStock;
        reduceNonPromotionStock(purchasedProduct.getName(), remainingBuyingStock);
        return purchasedProduct;
    }

    public PromotionProductGroup separatePromotion(List<Product> checkedProductStock) {
        Product promotionProduct = null;
        ArrayList<Product> nonPromotionProducts = new ArrayList<>();
        int totalStock = 0;

        for (Product product : checkedProductStock) {
            if (product.isPromotionActive()) {
                promotionProduct = product;
                totalStock += product.getStock();
                continue;
            }
            nonPromotionProducts.add(product);
            totalStock += product.getStock();
        }
        return new PromotionProductGroup(promotionProduct, nonPromotionProducts, totalStock);
    }

    public List<Product> getSameProductNameStocks(PurchaseProduct purchaseProduct) {
        List<Product> purchasableProducts = new ArrayList<>();

        purchasableProducts.addAll(
                stock.getOrDefault(purchaseProduct.getName(), new ArrayList<>())
        );

        if (purchasableProducts.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.NON_EXISTENT_PRODUCT.getErrorMessage());
        }

        return purchasableProducts;
    }

    public void checkProductStock(PurchaseProduct purchaseProduct, PromotionProductGroup promotionProductGroup) {
        if (promotionProductGroup.getTotalStock() < purchaseProduct.getQuantity()) {
            throw new IllegalArgumentException(ErrorMessage.EXCEEDS_STOCK_QUANTITY.getErrorMessage());
        }
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

    private void reducePromotionStock(String name, int quantity) {
        List<Product> products = stock.get(name);
        for (Product product : products) {
            if (product.isPromotionActive()) {
                product.reduceStock(quantity);
            }
        }
    }

    private void reduceNonPromotionStock(String name, int quantity) {
        List<Product> products = stock.get(name);
        for (Product product : products) {
            if (!product.isPromotionActive()) {
                product.reduceStock(quantity);
            }
        }
    }
}
