package store.model.receipt;

import store.model.PurchasedProduct;

import java.util.HashMap;
import java.util.List;

public class CostReceipt extends Receipt {
    private final boolean isMemberShip;
    public CostReceipt(List<PurchasedProduct> purchasedProducts, boolean isMemberShip) {
        this.isMemberShip = isMemberShip;
        HashMap<String, Integer> costProducts = new HashMap<>();
        totalCost = 0;
        totalQuantity = 0;
        for (PurchasedProduct purchasedProduct : purchasedProducts) {
            costProducts.put(purchasedProduct.getName(), purchasedProduct.getBuyingQuantity());
            totalCost += purchasedProduct.getBuyingQuantity() * purchasedProduct.getPrice();
            totalQuantity += purchasedProduct.getBuyingQuantity();
        }
        if (isMemberShip) {
            totalCost *= 0.7;
        }
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }
}
