package store.data.repository;

import store.data.entity.ProductEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public Optional<List<ProductEntity>> findPurchasableProductsByName(String name){
        ArrayList<ProductEntity> purchasableProducts = new ArrayList<>();
        for (ProductEntity stock : productStock){
            if (stock.equalsName(name)){
                purchasableProducts.add(stock);
            }
        }
        return Optional.of(purchasableProducts);
    }

    private List<ProductEntity> loadProductsFromFile() throws IOException {
        try (BufferedReader reader = createBufferedReader(PRODUCT_FILE_NAME)) {
            return reader.lines()
                    .skip(1)
                    .map(this::parseProductLine)
                    .collect(Collectors.toList());
        }
    }

    private ProductEntity parseProductLine(String line) {
        String[] parts = line.split(FILE_DELIMITER);
        String name = parts[0];
        String price = parts[1];
        String quantity = parts[2];
        String promotion = null;

        if (parts.length > 3 && !parts[3].isEmpty()) {
            promotion = parts[3];
        }

        return new ProductEntity(name, price, quantity, promotion);
    }
}
