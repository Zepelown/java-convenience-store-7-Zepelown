package store.controller;

import store.dto.ProductDto;
import store.model.PurchaseProduct;
import store.service.StoreBuyingService;
import store.service.StoreProductService;
import store.view.StoreInputView;
import store.view.StoreOutputView;

import java.io.IOException;
import java.util.List;

public class StoreController {
    private final StoreInputView storeInputView = new StoreInputView();
    private final StoreOutputView storeOutputView = new StoreOutputView();
    private StoreProductService storeProductService;
    private final StoreBuyingService storeBuyingService = new StoreBuyingService();

    public StoreController() {
        try{
            storeProductService = new StoreProductService();
        } catch (IOException e) {
            storeOutputView.printErrorMessage(e.getMessage());
        }
    }
    public void start() {
        storeOutputView.printProductStockNotification();
        List<ProductDto> products = loadProductStock();
        storeOutputView.printProductStock(products);

        List<PurchaseProduct> productsToBuy = getProductsToBuy();
    }

    private List<ProductDto> loadProductStock() {
        try {
            return storeProductService.loadProductStock();
        } catch (IOException e) {
            System.out.println("[ERROR]재고 로딩 실패");
        }
        return null;
    }

    private List<PurchaseProduct> getProductsToBuy() {
        while (true) {
            try {
                String userInput = storeInputView.getProductsToBuy();
                return storeBuyingService.getProductsToBuy(userInput);
            } catch (IllegalArgumentException e) {
                storeOutputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
