package store.model;

import store.util.ModelValidator;

public class PurchaseProduct {
    private static final String errorMessage = "[상품명-수량],[상품명-수량],... 으로 입력해 주세요.";
    private final String name;
    private final int quantity;

    public PurchaseProduct(String name, String quantity) {
        this.name = name;
        this.quantity = ModelValidator.parseInt(quantity, errorMessage);
        validate();
    }

    private void validate() {
        ModelValidator.validateString(name, errorMessage);
        ModelValidator.validateInt(quantity, errorMessage);
    }

}
