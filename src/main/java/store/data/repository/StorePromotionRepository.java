package store.data.repository;

import store.data.entity.PromotionEntity;
import store.exception.ErrorMessage;
import store.util.PromotionParser;

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

    public Optional<PromotionEntity> loadPromotion(String promotionName) {
        return Optional.ofNullable(promotions.get(promotionName));
    }

    private Map<String, PromotionEntity> loadPromotionsFromFile() throws IOException {
        try (BufferedReader reader = createBufferedReader(PROMOTION_FILE_NAME)) {
            return reader.lines()
                    .skip(1)
                    .map(PromotionParser::parsePromotionLine)
                    .collect(Collectors.toMap(PromotionEntity::getName, promotionEntity -> promotionEntity));
        }
    }

}
