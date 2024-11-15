package store.model.receipt;

import store.model.PurchasedProduct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreeReceipt extends Receipt {
    public FreeReceipt(List<PurchasedProduct> purchasedProducts) {
        this.purchasedProducts = new HashMap<>();
        for (PurchasedProduct purchasedProduct : purchasedProducts) {
            TotalCostPerProduct totalCostPerProduct = new TotalCostPerProduct(purchasedProduct.getFreeQuantity(), purchasedProduct.getFreeQuantity() * purchasedProduct.getPrice());
            this.purchasedProducts.put(purchasedProduct.getName(), totalCostPerProduct);
            totalCost += purchasedProduct.getFreeQuantity() * purchasedProduct.getPrice();
            totalQuantity += purchasedProduct.getFreeQuantity();
        }
    }

    public Map<String, TotalCostPerProduct> getProducts() {
        return purchasedProducts;
    }

}
