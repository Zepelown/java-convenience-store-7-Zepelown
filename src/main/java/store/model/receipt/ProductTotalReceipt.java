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

    public CostReceipt getCostReceipt() {
        return costReceipt;
    }

    public int getCostQuantity() {
        return costReceipt.getTotalQuantity();
    }

    public int getTotalQuantity() {
        return costReceipt.totalQuantity + freeReceipt.totalQuantity;
    }

    public FreeReceipt getFreeReceipt() {
        return freeReceipt;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getMemberDiscount() {
        if (!costReceipt.isMemberShip()) {
            return 0;
        }
        return (int) (totalCost * 0.3);
    }

    public int getFreeCost() {
        return freeCost;
    }

    public int getFinalCost(){
        if (!costReceipt.isMemberShip()) {
            return totalCost;
        }
        return (int) (totalCost * 0.7);
    }
}
