package store.service;

import store.data.entity.ProductEntity;
import store.data.repository.StoreProductRepository;
import store.data.repository.StorePromotionRepository;
import store.dto.ProductDto;
import store.exception.ErrorMessage;
import store.model.Product;
import store.model.Promotion;
import store.model.PromotionProductGroup;
import store.model.PurchaseProduct;

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

    public void buyProduct(PurchaseProduct purchaseProduct, PromotionProductGroup promotionProductGroup) {
        int promotionStock = promotionProductGroup.getPromotionProduct().getStock();
        int requiredStock = purchaseProduct.getQuantity();

        if (requiredStock <= promotionStock) {
            reducePromotionStock(purchaseProduct.getName(), purchaseProduct.getQuantity());
            return;
        }

        reducePromotionStock(purchaseProduct.getName(), promotionStock);
        int remainingBuyingStock = purchaseProduct.getQuantity() - promotionStock;
        reducePromotionStock(purchaseProduct.getName(), remainingBuyingStock);
    }

    public PromotionProductGroup separatePromotion(List<Product> checkedProductStock) {
        Product promotionProduct = null;
        ArrayList<Product> nonPromotionProducts = new ArrayList<>();
        int totalStock = 0;

        for (Product product : checkedProductStock) {
            if (product.isPromotionActive()) {
                promotionProduct = product;
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
}
