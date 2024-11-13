package store.exception;

public enum ErrorMessage {
    INVALID_BUYING_PRODUCT_INPUT_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NON_EXISTENT_PRODUCT("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    EXCEEDS_STOCK_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    ETC_ERROR("잘못된 입력입니다. 다시 입력해 주세요."),
    INVALID_FILE_ERROR("잘못된 파일 형식입니다.");

    private final String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
