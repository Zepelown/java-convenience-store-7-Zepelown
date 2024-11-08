package store.service;

import store.data.entity.Product;
import store.data.repository.StoreProductRepository;
import store.dto.ProductDto;

import java.util.List;
import java.util.stream.Collectors;

public class StoreProductService {
    private final StoreProductRepository storeProductRepository;

    public StoreProductService(StoreProductRepository storeProductRepository) {
        this.storeProductRepository = storeProductRepository;
    }


    public List<ProductDto> loadProductStock() {
        return storeProductRepository.loadProductStock().stream()
                .map(Product::toDto)
                .collect(Collectors.toList());
    }

}
