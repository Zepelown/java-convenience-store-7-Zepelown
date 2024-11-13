package store.service;

import store.model.PurchasedProduct;
import store.model.receipt.TotalReceipt;

import java.util.List;

public class StoreReceiptService {
    public TotalReceipt calculateReceipt(List<PurchasedProduct> purchasedProducts, boolean isMembership) {
        return new TotalReceipt(purchasedProducts, isMembership);
    }

}
