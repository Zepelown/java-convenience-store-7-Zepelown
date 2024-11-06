package store.view;

public class StoreOutputView {
    private final String PRODUCT_STOCK_NOTIFICATION = "안녕하세요. W편의점입니다.\n" +
            "현재 보유하고 있는 상품입니다.";

    public void printProductStockNotification() {
        System.out.println(PRODUCT_STOCK_NOTIFICATION);
    }
}
