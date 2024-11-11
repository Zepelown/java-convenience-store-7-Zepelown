package store.view;

import store.model.Product;
import store.model.Promotion;
import store.model.receipt.CostReceipt;
import store.model.receipt.FreeReceipt;
import store.model.receipt.TotalReceipt;
import store.model.receipt.TotalCostPerProduct;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StoreOutputView {
    private final DecimalFormat decimalFormat = new DecimalFormat("#,##0원");
    private final DecimalFormat receiptDecimalFormat = new DecimalFormat("#,##0");


    public void printProductStockNotification() {
        System.out.println(ViewConstants.PRODUCT_STOCK_NOTIFICATION);
    }

    public void printProductStock(HashMap<String, List<Product>> stock) {
        for (Map.Entry<String, List<Product>> entry : stock.entrySet()) {
            String productName = entry.getKey();
            List<Product> products = entry.getValue();

            printProductDetails(products, productName);

            if (isPromotionOnly(products)) {
                Product product = products.getFirst();
                printEmptyProduct(productName, product.getPrice());
            }
        }
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(ViewConstants.ERROR_MESSAGE_PREFIX + errorMessage);
    }

    public void printReceipt(TotalReceipt totalReceipt) {
        System.out.println(ViewConstants.RECEIPT_HEADER);
        printCostReceipt(totalReceipt.getCostReceipt());
        printFreeReceipt(totalReceipt.getFreeReceipt());
        printTotalCost(totalReceipt);
    }

    private void printCostReceipt(CostReceipt costReceipt) {
        for (Map.Entry<String, TotalCostPerProduct> entry : costReceipt.getProducts().entrySet()) {
            String productName = entry.getKey();
            TotalCostPerProduct totalCostPerProduct = entry.getValue();
            System.out.println(productName + "\t\t" + totalCostPerProduct.getQuantity() + "\t" + receiptDecimalFormat.format(totalCostPerProduct.getTotalCost()));
        }
    }

    private void printFreeReceipt(FreeReceipt freeReceipt) {
        System.out.println(ViewConstants.FREE_RECEIPT_HEADER);
        for (Map.Entry<String, TotalCostPerProduct> entry : freeReceipt.getProducts().entrySet()) {
            String productName = entry.getKey();
            TotalCostPerProduct totalCostPerProduct = entry.getValue();
            System.out.println(productName + "\t\t" + totalCostPerProduct.getQuantity() + "\t");
        }
        System.out.println(ViewConstants.RECEIPT_FOOTER);
    }

    private void printTotalCost(TotalReceipt totalReceipt) {
        System.out.println(ViewConstants.TOTAL_COST_PREFIX + totalReceipt.getTotalQuantity() + "\t" + receiptDecimalFormat.format(totalReceipt.getTotalCost()));
        System.out.println(ViewConstants.FREE_COST_PREFIX + receiptDecimalFormat.format(totalReceipt.getFreeCost()));
        System.out.println(ViewConstants.MEMBER_DISCOUNT_PREFIX+ receiptDecimalFormat.format(totalReceipt.getMemberDiscount()));
        System.out.println(ViewConstants.FINAL_COST_PREFIX + receiptDecimalFormat.format(totalReceipt.getFinalCost()));
    }

    private void printProductDetails(List<Product> products, String productName) {
        for (Product product : products) {
            printProductDetail(product);
        }
    }

    private void printProductDetail(Product product){
        StringBuilder result = new StringBuilder();
        result.append("- ").append(product.getName())
                .append(" ").append(decimalFormat.format(product.getPrice()));

        if (product.getStock() == 0) {
            result.append(" ").append(ViewConstants.NO_STOCK_LABEL);
        }

        if (product.getStock() > 0) {
            result.append(" ").append(product.getStock()).append("개");
        }

        addPromotionInfo(result, product);
        System.out.println(result);
    }

    private void addPromotionInfo(StringBuilder result, Product product) {
        String promotion = Optional.ofNullable(product.getPromotion())
                .map(Promotion::getName)
                .filter(p -> !"null".equalsIgnoreCase(p))
                .orElse("");

        if (!promotion.isEmpty()) {
            result.append(" ").append(promotion);
        }
    }

    private boolean isPromotionOnly(List<Product> products) {
        if (products.size() == 1 && products.getFirst().getPromotion().isPromotionApplicable()) {
            return true;
        }
        return false;
    }

    private void printEmptyProduct(String productName, int price) {
        StringBuilder result = new StringBuilder();
        result.append("- ").append(productName);
        result.append(" ").append(decimalFormat.format(price));
        result.append(" ").append(ViewConstants.NO_STOCK_LABEL);
        System.out.println(result);
    }
}
