package store.util;

import store.data.entity.ProductEntity;

public class ProductParser {
    private static final String FILE_DELIMITER = ",";

    public static ProductEntity parseProductLine(String line) {
        EntityValidator.validateString(line);

        String[] parts = line.split(FILE_DELIMITER);

        EntityValidator.validateLength(parts.length, 4);
        String name = parts[0];
        String price = parts[1];
        String quantity = parts[2];
        String promotion = parts[3];

        return new ProductEntity(name, price, quantity, promotion);
    }
}
