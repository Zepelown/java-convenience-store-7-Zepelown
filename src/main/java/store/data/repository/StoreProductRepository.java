package store.data.repository;

import store.data.entity.ProductEntity;
import store.exception.ErrorMessage;
import store.util.ProductParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class StoreProductRepository implements StoreRepository {
    private final String PRODUCT_FILE_NAME = "products.md";

    private List<ProductEntity> productStock;

    public StoreProductRepository() throws IOException {
        productStock = loadProductsFromFile();
    }

    public List<ProductEntity> loadProductStock() {
        return productStock;
    }

    private List<ProductEntity> loadProductsFromFile() throws IOException {
        try (BufferedReader reader = createBufferedReader(PRODUCT_FILE_NAME)) {
            return reader.lines()
                    .skip(1)
                    .map(ProductParser::parseProductLine)
                    .collect(Collectors.toList());
        }
    }


}
