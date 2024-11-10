package store.controller;

import store.data.repository.StoreProductRepository;
import store.data.repository.StorePromotionRepository;
import store.dto.ProductDto;
import store.dto.SeparatedProducts;
import store.model.Product;
import store.model.PurchaseProduct;
import store.model.PurchaseProductFactory;
import store.service.StoreStockService;
import store.view.StoreInputView;
import store.view.StoreOutputView;

import java.util.List;

public class StoreController {
    private final StoreProductRepository storeProductRepository;
    private final StorePromotionRepository storePromotionRepository;
    private final StoreInputView storeInputView;
    private final StoreOutputView storeOutputView;
    private final PurchaseProductFactory purchaseProductFactory = new PurchaseProductFactory();
    private final StoreStockService storeStockService;

    public StoreController(StoreProductRepository storeProductRepository, StorePromotionRepository storePromotionRepository, StoreInputView storeInputView, StoreOutputView storeOutputView) {
        this.storeProductRepository = storeProductRepository;
        this.storePromotionRepository = storePromotionRepository;
        this.storeInputView = storeInputView;
        this.storeOutputView = storeOutputView;

        storeStockService = new StoreStockService(storeProductRepository, storePromotionRepository);
    }

    public void start() {
        storeOutputView.printProductStockNotification();
        List<ProductDto> products = storeStockService.loadProductStock();
        storeOutputView.printProductStock(products);

        List<PurchaseProduct> productsToBuy = getProductsToBuy();
        for (PurchaseProduct purchaseProduct : productsToBuy){
            List<Product> sameProductNameStocks = storeStockService.getSameProductNameStocks(purchaseProduct);
            List<Product> checkedProductStock = storeStockService.checkProductStock(purchaseProduct, sameProductNameStocks);
            SeparatedProducts separatedProducts = storeStockService.separatePromotion(purchaseProduct,checkedProductStock);
            storeStockService.checkInsufficientBonusPromotionQuantity(purchaseProduct,separatedProducts);
        }
    }

    private List<PurchaseProduct> getProductsToBuy() {
        while (true) {
            try {
                String userInput = storeInputView.getProductsToBuy();
                return purchaseProductFactory.parsePurchaseProduct(userInput);
            } catch (IllegalArgumentException e) {
                storeOutputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
