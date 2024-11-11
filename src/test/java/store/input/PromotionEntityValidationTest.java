package store.input;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import store.data.entity.ProductEntity;
import store.data.repository.StoreProductRepository;
import store.data.repository.StorePromotionRepository;
import store.util.PromotionParser;

import java.io.IOException;

public class PromotionEntityValidationTest {
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1,2,3,4,5,6","반짝할인,1,1,2024-11,2024-11","반짝할인,-1,1,2024-11-01,2024-11-30","반짝할인,1,-1,2024-11-01,2024-11-30"})
    public void 프로모션_파일_입력은_양식을_지키지_못하면_실패한다(String input){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            PromotionParser.parsePromotionLine(input);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"탄산2+1,2,1,2024-01-01,2024-12-31", "MD추천상품,1,1,2024-01-01,2024-12-31", "반짝할인,1,1,2024-11-01,2024-11-30"})
    public void 프로모션_파일_입력은_양식이_올바르면_성공한다(String input){
        Assertions.assertDoesNotThrow(() -> {
            PromotionParser.parsePromotionLine(input);
        });
    }
}
