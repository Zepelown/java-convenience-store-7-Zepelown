package store.model.receipt;

import store.model.PurchasedProduct;

import java.util.HashMap;
import java.util.List;

public class FreeReceipt extends Receipt{
    public FreeReceipt(List<PurchasedProduct> purchasedProducts) {
        HashMap<String, Integer> freeProducts = new HashMap<>();
        totalCost = 0;
        totalQuantity = 0;
        for (PurchasedProduct purchasedProduct : purchasedProducts){
            freeProducts.put(purchasedProduct.getName(),purchasedProduct.getFreeQuantity());
            totalCost += purchasedProduct.getFreeQuantity() * purchasedProduct.getPrice();
            totalQuantity += purchasedProduct.getFreeQuantity();
        }
    }

}
