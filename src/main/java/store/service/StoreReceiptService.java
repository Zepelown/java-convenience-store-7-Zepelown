package store.service;

import store.model.PurchasedProduct;
import store.model.receipt.ProductTotalReceipt;

import java.util.List;

public class StoreReceiptService {
    public ProductTotalReceipt calculateReceipt(List<PurchasedProduct> purchasedProducts, boolean isMembership) {
        return new ProductTotalReceipt(purchasedProducts, isMembership);
    }

}
