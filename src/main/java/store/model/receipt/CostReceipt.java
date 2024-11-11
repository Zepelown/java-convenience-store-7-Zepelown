package store.model.receipt;

import store.model.PurchasedProduct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CostReceipt extends Receipt {
    private final boolean isMemberShip;

    public CostReceipt(List<PurchasedProduct> purchasedProducts, boolean isMemberShip) {
        this.isMemberShip = isMemberShip;
        this.purchasedProducts = new HashMap<>();
        for (PurchasedProduct purchasedProduct : purchasedProducts) {
            TotalCostPerProduct totalCostPerProduct = new TotalCostPerProduct(purchasedProduct.getBuyingQuantity(), purchasedProduct.getBuyingQuantity() * purchasedProduct.getPrice());
            this.purchasedProducts.put(purchasedProduct.getName(), totalCostPerProduct);
            totalCost += purchasedProduct.getBuyingQuantity() * purchasedProduct.getPrice();
            totalQuantity += purchasedProduct.getBuyingQuantity();
        }
    }

    public boolean isMemberShip() {
        return isMemberShip;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public Map<String, TotalCostPerProduct> getProducts() {
        return purchasedProducts;
    }

}
