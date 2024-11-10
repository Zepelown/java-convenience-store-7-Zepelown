package store.data.entity;

import store.model.Promotion;
import store.util.EntityValidator;

import java.time.LocalDate;

public class PromotionEntity {
    private String name;
    private int buy;
    private int get;
    private LocalDate startDate;
    private LocalDate endDate;

    public PromotionEntity(String name, String buy, String get, String startDate, String endDate) {
        this.name = name;
        this.buy = EntityValidator.parseInt(buy);
        this.get = EntityValidator.parseInt(get);
        this.startDate = EntityValidator.parseDate(startDate);
        this.endDate = EntityValidator.parseDate(endDate);
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
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
