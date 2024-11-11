package store.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import store.data.repository.StoreProductRepository;
import store.data.repository.StorePromotionRepository;
import store.dto.InsufficientBonusProductDto;
import store.dto.PromotionQuantityOverStockDto;
import store.model.Product;
import store.model.PromotionProductGroup;
import store.model.PurchaseProduct;
import store.model.PurchasedProduct;
import store.service.StoreStockService;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseProductTest {
    private static StoreProductRepository storeProductRepository;
    private static StorePromotionRepository storePromotionRepository;
    private static StoreStockService storeStockService;

    @BeforeAll
    public static void initSetting() throws IOException {
        storeProductRepository = new StoreProductRepository();
        storePromotionRepository = new StorePromotionRepository();
        storeStockService = new StoreStockService(storeProductRepository, storePromotionRepository);
    }

    private PromotionProductGroup getPromotionProductGroup(PurchaseProduct purchaseProduct) {
        List<Product> sameProductNameStocks = storeStockService.getSameProductNameStocks(purchaseProduct);
        return storeStockService.separatePromotion(sameProductNameStocks);
    }

    private PurchasedProduct createPurchasedProduct(PurchaseProduct purchaseProduct, PromotionProductGroup promotionProductGroup) {
        return new PurchasedProduct(
                purchaseProduct.getName(),
                purchaseProduct.getQuantity(),
                0, 0,
                promotionProductGroup.getProductCost(),
                promotionProductGroup.getProductPromotion()
        );
    }

    @Test
    public void 총_구매량이_전체_재고량_보다_적으면_구매에_성공한다() {
        Assertions.assertDoesNotThrow(() -> {
            PurchaseProduct purchaseProduct = new PurchaseProduct("사이다", "2");
            PromotionProductGroup promotionProductGroup = getPromotionProductGroup(purchaseProduct);
            storeStockService.checkProductStock(purchaseProduct, promotionProductGroup);
        });
    }

    @Test
    public void 프로모션_적용이_가능한_상품에_대해_고객이_해당_수량보다_적게_가져온_경우_부족한_수량을_계산한다() {
        PurchaseProduct purchaseProduct = new PurchaseProduct("사이다", "2");
        PromotionProductGroup promotionProductGroup = getPromotionProductGroup(purchaseProduct);
        PurchasedProduct purchasedProduct = createPurchasedProduct(purchaseProduct, promotionProductGroup);

        InsufficientBonusProductDto result = promotionProductGroup.checkInsufficientBonusPromotionQuantity(purchasedProduct);
        assertThat(result.getQuantity()).isEqualTo(1);
    }

    @Test
    public void 프로모션_적용이_가능한_상품에_대해_고객이_해당_수량에_맞게_가져온_경우_계산하지_않는다() {
        PurchaseProduct purchaseProduct = new PurchaseProduct("사이다", "3");
        PromotionProductGroup promotionProductGroup = getPromotionProductGroup(purchaseProduct);
        PurchasedProduct purchasedProduct = createPurchasedProduct(purchaseProduct, promotionProductGroup);

        InsufficientBonusProductDto result = promotionProductGroup.checkInsufficientBonusPromotionQuantity(purchasedProduct);
        assertThat(result).isNull();
    }

    @Test
    public void 프로모션_재고가_부족하여_일부_수량을_프로모션_혜택_없이_결제해야_하는_경우_그_수량을_계산한다() {
        PurchaseProduct purchaseProduct = new PurchaseProduct("사이다", "12");
        PromotionProductGroup promotionProductGroup = getPromotionProductGroup(purchaseProduct);
        PurchasedProduct purchasedProduct = createPurchasedProduct(purchaseProduct, promotionProductGroup);

        PromotionQuantityOverStockDto result = promotionProductGroup.checkPromotionQuantityOverStock(purchasedProduct);
        assertThat(result.getQuantity()).isEqualTo(4);
    }

    @Test
    public void 프로모션_재고가_부족하여_일부_수량을_프로모션_혜택_없이_결제해야_하는_경우가_아니면_계산하지_않는다() {
        PurchaseProduct purchaseProduct = new PurchaseProduct("사이다", "8");
        PromotionProductGroup promotionProductGroup = getPromotionProductGroup(purchaseProduct);
        PurchasedProduct purchasedProduct = createPurchasedProduct(purchaseProduct, promotionProductGroup);

        PromotionQuantityOverStockDto result = promotionProductGroup.checkPromotionQuantityOverStock(purchasedProduct);
        assertThat(result).isNull();
    }
}
