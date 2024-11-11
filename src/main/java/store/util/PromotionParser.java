package store.util;

import store.data.entity.PromotionEntity;

public class PromotionParser {
    private static final String FILE_DELIMITER = ",";

    public static PromotionEntity parsePromotionLine(String line) {
        EntityValidator.validateString(line);

        String[] parts = line.split(FILE_DELIMITER);
        EntityValidator.validateLength(parts.length, 5);

        String name = parts[0];
        String buy = parts[1];
        String get = parts[2];
        String startDate = parts[3];
        String endDate = parts[4];

        return new PromotionEntity(name, buy, get, startDate, endDate);
    }
}
