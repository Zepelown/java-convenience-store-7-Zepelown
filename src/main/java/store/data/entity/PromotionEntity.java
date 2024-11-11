package store.data.entity;

import store.model.Promotion;
import store.util.EntityValidator;

import java.time.LocalDate;

public class PromotionEntity {
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public PromotionEntity(String name, String buy, String get, String startDate, String endDate) {
        this.name = name;
        this.buy = EntityValidator.parseInt(buy);
        this.get = EntityValidator.parseInt(get);
        this.startDate = EntityValidator.parseDate(startDate);
        this.endDate = EntityValidator.parseDate(endDate);
        validate();
    }

    public String getName() {
        return name;
    }

    public Promotion toPromotion() {
        return new Promotion(name, buy, get, startDate, endDate);
    }

    private void validate() {
        EntityValidator.validateString(name);
        EntityValidator.validateInt(buy);
        EntityValidator.validateInt(get);
    }
}
