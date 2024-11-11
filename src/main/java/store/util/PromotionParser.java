package store.util;

import store.data.entity.PromotionEntity;
import store.exception.ErrorMessage;

public class PromotionParser {
    private static final String FILE_DELIMITER = ",";
    public static PromotionEntity parsePromotionLine(String line) {
        if (line == null || line.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.ETC_ERROR.getErrorMessage());
        }

        String[] parts = line.split(FILE_DELIMITER);
        if (parts.length > 5) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FILE_ERROR.getErrorMessage());
        }

        String name = parts[0];
        String buy = parts[1];
        String get = parts[2];
        String startDate = parts[3];
        String endDate = parts[4];

        return new PromotionEntity(name, buy, get, startDate, endDate);
    }
}
