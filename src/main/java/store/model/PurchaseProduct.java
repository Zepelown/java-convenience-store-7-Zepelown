package store.model;

import store.exception.ErrorMessage;
import store.util.ModelValidator;

public class PurchaseProduct {
    private final String name;
    private final int quantity;

    public PurchaseProduct(String name, String quantity) {
        this.name = name;
        this.quantity = ModelValidator.parseInt(quantity, ErrorMessage.INVALID_BUYING_PRODUCT_INPUT_FORMAT.getErrorMessage());
        validate();
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    private void validate() {
        ModelValidator.validateString(name, ErrorMessage.INVALID_BUYING_PRODUCT_INPUT_FORMAT.getErrorMessage());
        ModelValidator.validateInt(quantity, ErrorMessage.INVALID_BUYING_PRODUCT_INPUT_FORMAT.getErrorMessage());
    }

}
