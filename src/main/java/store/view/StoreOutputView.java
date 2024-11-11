package store.view;

import store.model.Product;
import store.model.Promotion;
import store.model.receipt.CostReceipt;
import store.model.receipt.FreeReceipt;
import store.model.receipt.TotalCostPerProduct;
import store.model.receipt.ProductTotalReceipt;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StoreOutputView {
    private final DecimalFormat decimalFormat = new DecimalFormat("#,##0원");
    private final DecimalFormat receiptDecimalFormat = new DecimalFormat("#,##0");
    private final String PRODUCT_STOCK_NOTIFICATION = "안녕하세요. W편의점입니다.\n" +
            "현재 보유하고 있는 상품입니다.";
    private final String ERROR_MESSAGE_PREFIX = "[ERROR]";

    public void printProductStockNotification() {
        System.out.println(PRODUCT_STOCK_NOTIFICATION);
    }

    public void printProductStock(HashMap<String, List<Product>> stock) {
        for (Map.Entry<String, List<Product>> entry : stock.entrySet()) {
            String productName = entry.getKey();
            List<Product> products = entry.getValue();
            for (Product product : products) {
                StringBuilder result = new StringBuilder();
                result.append("- ").append(productName);
                result.append(" ").append(decimalFormat.format(product.getPrice()));
                result.append(" ").append(product.getStock()).append("개");

                String promotion = Optional.ofNullable(product.getPromotion())
                        .map(Promotion::getName)
                        .filter(p -> !"null".equalsIgnoreCase(p))
                        .orElse("");

                if (!promotion.isEmpty()) {
                    result.append(" ").append(promotion);
                }

                System.out.println(result);
            }
            if (products.size() == 1){
                Product product = products.getFirst();
                if (product.getPromotion().isPromotionApplicable()){
                    printEmptyProduct(productName,product.getPrice());
                }
            }
        }
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(ERROR_MESSAGE_PREFIX + errorMessage);
    }

    public void printReceipt(ProductTotalReceipt totalReceipt){
        System.out.println("===========W 편의점=============");
        System.out.println("상품명\t\t수량\t금액");
        printCostReceipt(totalReceipt.getCostReceipt());
        printFreeReceipt(totalReceipt.getFreeReceipt());
        printTotalCost(totalReceipt);
    }

    private void printCostReceipt(CostReceipt costReceipt){
        for (Map.Entry<String, TotalCostPerProduct> entry : costReceipt.getProducts().entrySet()) {
            String productName = entry.getKey();
            TotalCostPerProduct totalCostPerProduct = entry.getValue();
            System.out.println(productName + "\t\t" + totalCostPerProduct.getQuantity()+"\t"+ receiptDecimalFormat.format(totalCostPerProduct.getTotalCost()));
        }
    }
    private void printFreeReceipt(FreeReceipt freeReceipt){
        System.out.println("===========증\t정=============");
        for (Map.Entry<String, TotalCostPerProduct> entry : freeReceipt.getProducts().entrySet()) {
            String productName = entry.getKey();
            TotalCostPerProduct totalCostPerProduct = entry.getValue();
            System.out.println(productName + "\t\t" + totalCostPerProduct.getQuantity()+"\t");
        }
        System.out.println("==============================");
    }
    private void printTotalCost(ProductTotalReceipt totalReceipt){
        System.out.println("총구매액\t\t"+totalReceipt.getTotalQuantity()+"\t"+receiptDecimalFormat.format(totalReceipt.getTotalCost()));
        System.out.println("행사할인\t\t\t-"+receiptDecimalFormat.format(totalReceipt.getFreeCost()));
        System.out.println("멤버십할인\t\t\t-"+receiptDecimalFormat.format(totalReceipt.getMemberDiscount()));
        System.out.println("내실돈\t\t\t"+receiptDecimalFormat.format(totalReceipt.getFinalCost()));
    }

    private void printEmptyProduct(String productName, int price){
        StringBuilder result = new StringBuilder();
        result.append("- ").append(productName);
        result.append(" ").append(decimalFormat.format(price));
        result.append(" ").append("재고 없음");
        System.out.println(result);
    }
}
