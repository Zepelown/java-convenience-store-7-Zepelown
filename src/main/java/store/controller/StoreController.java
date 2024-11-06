package store.controller;

import store.data.entity.Product;
import store.service.StoreDataService;

import java.io.IOException;
import java.util.List;

public class StoreController {
    private final StoreDataService storeDataService = new StoreDataService();
    public void start(){
        initStoreSetting();
    }
    private void initStoreSetting(){
        try{
            List<Product> products = storeDataService.loadProductStock();
        } catch (IOException e){
            System.out.println("[ERROR]재고 로딩 실패");
        }
    }
}
