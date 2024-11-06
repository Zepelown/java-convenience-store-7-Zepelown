package store.service;

import store.data.entity.Product;
import store.data.entity.Promotion;
import store.data.repository.StoreRepository;

import java.io.IOException;
import java.util.List;

public class StoreDataService {
    private final StoreRepository storeRepository = new StoreRepository();
    public List<Product> loadProductStock() throws IOException {
        return storeRepository.loadProductsFromFile();
    }
    public List<Promotion> loadPromotions() throws IOException{
        return storeRepository.loadPromotionsFromFile();
    }

}
