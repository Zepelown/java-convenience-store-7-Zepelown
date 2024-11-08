package store.data.repository;

import store.data.entity.Promotion;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class StorePromotionRepository implements StoreRepository {
    private final String PROMOTION_FILE_NAME = "promotions.md";

    private List<Promotion> promotions;

    public StorePromotionRepository() throws IOException {
        promotions = loadPromotionsFromFile();
    }

    public List<Promotion> loadPromotions() {
        return promotions;
    }

    private List<Promotion> loadPromotionsFromFile() throws IOException {
        try (BufferedReader reader = createBufferedReader(PROMOTION_FILE_NAME)) {
            return reader.lines()
                    .skip(1)
                    .map(this::parsePromotionLine)
                    .collect(Collectors.toList());
        }
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
