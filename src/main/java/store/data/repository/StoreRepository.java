package store.data.repository;

import store.data.entity.Product;
import store.data.entity.Promotion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class StoreRepository {
    private final String FILE_DELIMITER = ",";
    private final String PRODUCT_FILE_NAME = "products.md";
    private final String PROMOTION_FILE_NAME = "promotions.md";

    public List<Product> loadProductsFromFile() throws IOException {
        try (BufferedReader reader = createBufferedReader(PRODUCT_FILE_NAME)) {
            return reader.lines()
                    .skip(1)
                    .map(this::parseProductLine)
                    .collect(Collectors.toList());
        }
    }

    public List<Promotion> loadPromotionsFromFile() throws IOException {
        try (BufferedReader reader = createBufferedReader(PROMOTION_FILE_NAME)) {
            return reader.lines()
                    .skip(1)
                    .map(this::parsePromotionLine)
                    .collect(Collectors.toList());
        }
    }


    private BufferedReader createBufferedReader(String fileName) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("[ERROR]" + fileName + " 파일을 찾을 수 없습니다.");
        }
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    private Product parseProductLine(String line) {
        String[] parts = line.split(FILE_DELIMITER);
        String name = parts[0];
        String price = parts[1];
        String quantity = parts[2];
        String promotion = null;

        if (parts.length > 3 && !parts[3].isEmpty()) {
            promotion = parts[3];
        }

        return new Product(name, price, quantity, promotion);
    }

    private Promotion parsePromotionLine(String line) {
        String[] parts = line.split(FILE_DELIMITER);
        String name = parts[0];
        String buy = parts[1];
        String get = parts[2];
        String startDate = parts[3];
        String endDate = parts[4];
        return new Promotion(name, buy, get, startDate, endDate);
    }
}
