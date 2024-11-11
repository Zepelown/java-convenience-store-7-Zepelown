package store.data.repository;

import store.data.entity.PromotionEntity;
import store.exception.ErrorMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class StorePromotionRepository implements StoreRepository {
    private final String PROMOTION_FILE_NAME = "promotions.md";

    private Map<String, PromotionEntity> promotions;

    public StorePromotionRepository() throws IOException {
        promotions = loadPromotionsFromFile();
    }

    public Map<String, PromotionEntity> loadPromotions() {
        return promotions;
    }

    public Optional<PromotionEntity> loadPromotion(String promotionName) {
        return Optional.ofNullable(promotions.get(promotionName));
    }

    private Map<String, PromotionEntity> loadPromotionsFromFile() throws IOException {
        try (BufferedReader reader = createBufferedReader(PROMOTION_FILE_NAME)) {
            return reader.lines()
                    .skip(1)
                    .map(this::parsePromotionLine)
                    .collect(Collectors.toMap(PromotionEntity::getName, promotionEntity -> promotionEntity));
        }
    }

    public PromotionEntity parsePromotionLine(String line) {
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
