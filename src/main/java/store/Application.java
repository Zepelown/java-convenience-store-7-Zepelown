package store;

import store.controller.StoreController;
import store.data.repository.StoreProductRepository;
import store.data.repository.StorePromotionRepository;
import store.exception.ErrorMessage;
import store.view.StoreInputView;
import store.view.StoreOutputView;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        try {
            StoreInputView storeInputView = new StoreInputView();
            StoreOutputView storeOutputView = new StoreOutputView();
            StoreProductRepository storeProductRepository = new StoreProductRepository();
            StorePromotionRepository storePromotionRepository = new StorePromotionRepository();
            StoreController storeController = new StoreController(storeProductRepository, storePromotionRepository, storeInputView, storeOutputView);

            storeController.start();
        } catch (IOException e) {
            System.out.println("[ERROR]" + ErrorMessage.INVALID_FILE_ERROR);
        }
    }
}
