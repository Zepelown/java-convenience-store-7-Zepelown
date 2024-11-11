package store.input;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import store.data.repository.StoreProductRepository;
import store.util.ProductParser;

import java.io.IOException;

public class ProductEntityValidationTest {
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"콜라,1000,10","콜라"})
    public void 재고_파일_입력은_양식을_지키지_못하면_실패한다(String input){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ProductParser.parseProductLine(input);
        });
    }
    @ParameterizedTest
    @ValueSource(strings = {"콜라,1000,10,탄산2+1"})
    public void 재고_파일_입력은_양식을_지키면_성공한다(String input){
        Assertions.assertDoesNotThrow(() -> {
            ProductParser.parseProductLine(input);
        });
    }
}
