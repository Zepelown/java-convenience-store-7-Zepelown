package store.model.receipt;

import store.model.PurchasedProduct;

import java.util.List;

public class ProductTotalReceipt {
    private final CostReceipt costReceipt;
    private final FreeReceipt freeReceipt;
    private final int totalCost;
    private final int freeCost;

    public ProductTotalReceipt(List<PurchasedProduct> purchasedProducts, boolean isMemberShip) {
        costReceipt = new CostReceipt(purchasedProducts, isMemberShip);
        freeReceipt = new FreeReceipt(purchasedProducts);
        this.totalCost = costReceipt.totalCost;
        this.freeCost = freeReceipt.totalCost;
    }

    public Receipt getCostReceipt() {
        return costReceipt;
    }

    public int getCostQuantity() {
        return costReceipt.getTotalQuantity();
    }

    public int getTotalQuantity(){
        return costReceipt.totalQuantity + freeReceipt.totalQuantity;
    }

    public Receipt getFreeReceipt() {
        return freeReceipt;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getFreeCost() {
        return freeCost;
    }
}
