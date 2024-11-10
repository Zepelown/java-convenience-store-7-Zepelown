package store.view;

import store.dto.ProductDto;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

public class StoreOutputView {
    private final DecimalFormat decimalFormat = new DecimalFormat("#,##0원");
    private final String PRODUCT_STOCK_NOTIFICATION = "안녕하세요. W편의점입니다.\n" +
            "현재 보유하고 있는 상품입니다.";
    private final String ERROR_MESSAGE_PREFIX = "[ERROR]";

    public void printProductStockNotification() {
        System.out.println(PRODUCT_STOCK_NOTIFICATION);
    }

    public void printProductStock(List<ProductDto> products) {
        for (ProductDto product : products) {
            StringBuilder result = new StringBuilder();
            result.append("- " + product.getName());
            result.append(" " + decimalFormat.format(product.getPrice()));
            result.append(" " + product.getQuantity() + "개");
            String promotion = Optional.ofNullable(product.getPromotion())
                    .filter(p -> !"null".equalsIgnoreCase(p))
                    .orElse("재고없음");
            result.append(" ").append(promotion);
            System.out.println(result);
        }
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(ERROR_MESSAGE_PREFIX + errorMessage);
    }


}
