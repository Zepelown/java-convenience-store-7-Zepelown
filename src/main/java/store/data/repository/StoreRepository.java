package store.data.repository;

import store.data.entity.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class StoreRepository {
    private final String PRODUCT_FILE_NAME = "products.md";

    public List<Product> loadProductsFromFile() throws IOException {
        try (BufferedReader reader = createBufferedReader(PRODUCT_FILE_NAME)) {
            return reader.lines()
                    .skip(1)
                    .map(this::parseProductLine)
                    .collect(Collectors.toList());
        }
    }

    private BufferedReader createBufferedReader(String fileName) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    private Product parseProductLine(String line) {
        String[] parts = line.split(",");
        String name = parts[0];
        int price = Integer.parseInt(parts[1]);
        int quantity = Integer.parseInt(parts[2]);
        String promotion = null;

        if (parts.length > 3 && !parts[3].isEmpty()) {
            promotion = parts[3];
        }

        return new Product(name, price, quantity, promotion);
    }
}
