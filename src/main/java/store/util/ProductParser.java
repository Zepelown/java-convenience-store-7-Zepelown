package store.util;

import store.data.entity.ProductEntity;
import store.exception.ErrorMessage;

public class ProductParser {
    private static final String FILE_DELIMITER = ",";
    public static ProductEntity parseProductLine(String line) {
        if (line == null || line.isEmpty()){
            throw new IllegalArgumentException(ErrorMessage.ETC_ERROR.getErrorMessage());
        }
        String[] parts = line.split(FILE_DELIMITER);
        if (parts.length != 4) {
            throw new IllegalArgumentException(ErrorMessage.ETC_ERROR.getErrorMessage());
        }
        String name = parts[0];
        String price = parts[1];
        String quantity = parts[2];
        String promotion = parts[3];

        return new ProductEntity(name, price, quantity, promotion);
    }
}
