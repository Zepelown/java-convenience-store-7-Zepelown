package store.model.receipt;

import store.model.PurchasedProduct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Receipt {
    int totalCost = 0;
    int totalQuantity;
    Map<String, Integer> costProducts = new HashMap<>();

}
