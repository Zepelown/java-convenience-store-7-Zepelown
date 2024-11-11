package store.model.receipt;

import java.util.HashMap;
import java.util.Map;

public abstract class Receipt {
    int totalCost = 0;
    int totalQuantity =0;
    Map<String, TotalCostPerProduct> purchasedProducts = new HashMap<>();


}
