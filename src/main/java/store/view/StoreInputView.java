package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.dto.InsufficientBonusProductDto;
import store.dto.PromotionQuantityOverStockDto;

public class StoreInputView {
    public String getProductsToBuy() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return Console.readLine();
    }

    public String getConfirmInsufficientBonusPromotionProduct(InsufficientBonusProductDto insufficientBonusProduct) {
        System.out.println("현재 " + insufficientBonusProduct.getName() + "은(는) " + insufficientBonusProduct.getQuantity() + "개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
        return Console.readLine();
    }

    public String getPromotionQuantityOverStock(PromotionQuantityOverStockDto promotionQuantityOverStock) {
        System.out.println("현재 " + promotionQuantityOverStock.getName() + " " + promotionQuantityOverStock.getQuantity() + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
        return Console.readLine();
    }
    public String getMemberShipConfirm(){
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        return Console.readLine();
    }
}
