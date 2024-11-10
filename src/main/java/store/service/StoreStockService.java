package store.service;

import store.data.entity.ProductEntity;
import store.data.repository.StoreProductRepository;
import store.data.repository.StorePromotionRepository;
import store.dto.ProductDto;
import store.dto.SeparatedProducts;
import store.exception.ErrorMessage;
import store.model.Product;
import store.model.Promotion;
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

    public SeparatedProducts separatePromotion(PurchaseProduct purchaseProduct, List<Product> checkedProductStock) {
        Product promotionProduct = null;
        ArrayList<Product> nonPromotionProducts = new ArrayList<>();

        for (Product product : checkedProductStock) {
            if (product.isPromotionActive()){
                promotionProduct = product;
                continue;
            }
            nonPromotionProducts.add(product);
        }
        return new SeparatedProducts(promotionProduct, nonPromotionProducts);
    }

    public void checkInsufficientBonusPromotionQuantity(PurchaseProduct purchaseProduct,SeparatedProducts separatedProducts){
        Product promotionProduct = separatedProducts.getPromotionProduct();
        if (promotionProduct == null){
            return;
        }
        promotionProduct.checkInsufficientBonusPromotionQuantity(purchaseProduct.getQuantity());
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

    public List<Product> checkProductStock(PurchaseProduct purchaseProduct, List<Product> sameProductNameStocks) {
        List<Product> products = sameProductNameStocks.stream()
                .filter(it -> it.canPurchase(purchaseProduct.getQuantity()))
                .collect(Collectors.toList());
        if (products.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.EXCEEDS_STOCK_QUANTITY.getErrorMessage());
        }
        return products;
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
