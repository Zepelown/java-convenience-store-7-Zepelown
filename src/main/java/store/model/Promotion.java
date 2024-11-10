package store.model;

import camp.nextstep.edu.missionutils.DateTimes;
import store.model.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class Promotion {
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isPromotionApplicable() {
        if (startDate == null || endDate == null) {
            return false;
        }
        LocalDateTime now = DateTimes.now();
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);

        return (start.isBefore(now) || start.isEqual(now)) &&
                (end.isAfter(now) || end.isEqual(now));
    }


    public int calculatePromotionQuantity(int quantity) {
        int applicableSets = quantity / buy;
        return quantity + applicableSets * get;
    }
}
