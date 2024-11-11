package store.input;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import store.model.PurchaseProductFactory;
import store.util.ModelValidator;

public class PurchaseProductTest {
    PurchaseProductFactory purchaseProductFactory = new PurchaseProductFactory();

    @ParameterizedTest
    @ValueSource(strings = {"[콜라-10사이다-10","사이다,10"})
    public void 구매할_상품_입력은_양식을_지키지_못하면_실패한다(String input){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            purchaseProductFactory.parsePurchaseProduct(input);
        });
    }
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"asdf","1","`"})
    public void 구매할_상품_입력은_잘못된_값을_입력_하면_실패한다(String input){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            purchaseProductFactory.parsePurchaseProduct(input);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"콜라-10", "사이다-2", "콜라-1,사이다-5"})
    public void 구매할_상품_입력이_양식에_맞으면_성공적으로_처리된다(String input) {
        Assertions.assertDoesNotThrow(() -> {
            purchaseProductFactory.parsePurchaseProduct(input);
        });
    }
}
