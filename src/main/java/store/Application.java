package store;

import store.controller.StoreController;
import store.data.repository.StoreProductRepository;

public class Application {
    public static void main(String[] args) {
        StoreController storeController = new StoreController();
        storeController.start();
    }
}
