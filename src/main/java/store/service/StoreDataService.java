package store.service;

import store.data.entity.Product;
import store.data.entity.Promotion;
import store.data.repository.StoreRepository;
import store.dto.ProductDto;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class StoreDataService {
    private final StoreRepository storeRepository = new StoreRepository();

    public List<ProductDto> loadProductStock() throws IOException {
        return storeRepository.loadProductsFromFile().stream()
                .map(Product::toDto)
                .collect(Collectors.toList());
    }

    public List<Promotion> loadPromotions() throws IOException {
        return storeRepository.loadPromotionsFromFile();
    }

}
