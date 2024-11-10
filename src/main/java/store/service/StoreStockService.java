package store.service;

import store.data.entity.ProductEntity;
import store.data.repository.StoreProductRepository;
import store.data.repository.StorePromotionRepository;
import store.dto.ProductDto;
import store.exception.ErrorMessage;
import store.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public PurchasedProduct buyProduct(PurchaseProduct purchaseProduct, PromotionProductGroup promotionProductGroup) {
        int promotionStock = promotionProductGroup.getPromotionProduct().getStock();
        int requiredStock = purchaseProduct.getQuantity();
        PurchasedProduct purchasedProduct = new PurchasedProduct(purchaseProduct.getName(), 0, 0, promotionProductGroup.getProductCost(), promotionProductGroup.getProductPromotion());

        reducePromotionStock(purchaseProduct.getName(), promotionStock);
        purchasedProduct.addPromotionQuantity(purchaseProduct.getQuantity());
        if (requiredStock <= promotionStock) {
            return purchasedProduct;
        }

        int remainingBuyingStock = purchaseProduct.getQuantity() - promotionStock;
        reduceNonPromotionStock(purchaseProduct.getName(), remainingBuyingStock);
        purchasedProduct.addPromotionQuantity(purchaseProduct.getQuantity());
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
        if (promotionProductGroup.getTotalStock() <= purchaseProduct.getQuantity()) {
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
