package store.input;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import store.util.YesNoValidator;

public class YesNoValidationTest {
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"a", "y", "n", "사", "0", "`", "@"})
    public void Y_또는_N이_아니면_실패한다(String input) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            YesNoValidator.validateYN(input);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"Y", "N"})
    public void Y_또는_N이면_성공한다(String input) {
        Assertions.assertDoesNotThrow(() -> {
            YesNoValidator.validateYN(input);
        });
    }
}
