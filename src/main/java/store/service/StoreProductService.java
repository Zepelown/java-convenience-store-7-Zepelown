package store.service;

import store.data.entity.Product;
import store.data.repository.StoreProductRepository;
import store.dto.ProductDto;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class StoreProductService {
    private StoreProductRepository storeProductRepository = new StoreProductRepository();

    public StoreProductService() throws IOException {

    }

    public List<ProductDto> loadProductStock() throws IOException {
        return storeProductRepository.loadProductStock().stream()
                .map(Product::toDto)
                .collect(Collectors.toList());
    }

}
