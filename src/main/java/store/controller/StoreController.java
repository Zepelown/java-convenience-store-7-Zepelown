package store.controller;

import store.dto.ProductDto;
import store.model.PurchaseProduct;
import store.model.PurchaseProductFactory;
import store.service.StoreProductService;
import store.view.StoreInputView;
import store.view.StoreOutputView;

import java.io.IOException;
import java.util.List;

public class StoreController {
    private final StoreInputView storeInputView = new StoreInputView();
    private final StoreOutputView storeOutputView = new StoreOutputView();
    private StoreProductService storeProductService;
    private final PurchaseProductFactory purchaseProductFactory = new PurchaseProductFactory();

    public StoreController() {
        try{
            storeProductService = new StoreProductService();
        } catch (IOException e) {
            storeOutputView.printErrorMessage(e.getMessage());
        }
    }
    public void start() {
        storeOutputView.printProductStockNotification();
        List<ProductDto> products = storeProductService.loadProductStock();
        storeOutputView.printProductStock(products);

        List<PurchaseProduct> productsToBuy = getProductsToBuy();
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
