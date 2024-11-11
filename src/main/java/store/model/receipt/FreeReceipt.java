package store.model.receipt;

import store.model.PurchasedProduct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreeReceipt extends Receipt{
    public FreeReceipt(List<PurchasedProduct> purchasedProducts) {
        this.purchasedProducts = new HashMap<>();
        totalCost = 0;
        totalQuantity = 0;
        for (PurchasedProduct purchasedProduct : purchasedProducts){
            TotalCostPerProduct totalCostPerProduct = new TotalCostPerProduct(purchasedProduct.getBuyingQuantity(),purchasedProduct.getBuyingQuantity() * purchasedProduct.getPrice());
            this.purchasedProducts.put(purchasedProduct.getName(), totalCostPerProduct);
            totalCost += purchasedProduct.getFreeQuantity() * purchasedProduct.getPrice();
            totalQuantity += purchasedProduct.getFreeQuantity();
        }
    }

    public Map<String, TotalCostPerProduct> getProducts(){
        return purchasedProducts;
    }

}
