package store.controller;

import store.data.repository.StoreProductRepository;
import store.data.repository.StorePromotionRepository;
import store.dto.InsufficientBonusProductDto;
import store.dto.ProductDto;
import store.dto.PromotionProductGroup;
import store.dto.PromotionQuantityOverStockDto;
import store.exception.ErrorMessage;
import store.model.Product;
import store.model.PurchaseProduct;
import store.model.PurchaseProductFactory;
import store.service.StoreStockService;
import store.util.YesNoValidator;
import store.view.StoreInputView;
import store.view.StoreOutputView;

import java.util.List;

public class StoreController {
    private final StoreProductRepository storeProductRepository;
    private final StorePromotionRepository storePromotionRepository;
    private final StoreInputView storeInputView;
    private final StoreOutputView storeOutputView;
    private final PurchaseProductFactory purchaseProductFactory = new PurchaseProductFactory();
    private final StoreStockService storeStockService;

    public StoreController(StoreProductRepository storeProductRepository, StorePromotionRepository storePromotionRepository, StoreInputView storeInputView, StoreOutputView storeOutputView) {
        this.storeProductRepository = storeProductRepository;
        this.storePromotionRepository = storePromotionRepository;
        this.storeInputView = storeInputView;
        this.storeOutputView = storeOutputView;

        storeStockService = new StoreStockService(storeProductRepository, storePromotionRepository);
    }

    public void start() {
        storeOutputView.printProductStockNotification();
        List<ProductDto> products = storeStockService.loadProductStock();
        storeOutputView.printProductStock(products);

        List<PurchaseProduct> productsToBuy = getProductsToBuy();

        for (PurchaseProduct purchaseProduct : productsToBuy) {
            PromotionProductGroup promotionProductGroup = getPurchasableProducts(purchaseProduct);
            checkInsufficientBonusPromotionQuantity(purchaseProduct, promotionProductGroup);
            checkPromotionQuantityOverStock(purchaseProduct, promotionProductGroup);
        }

    }

    private List<PurchaseProduct> getProductsToBuy() {
        while (true) {
            try {
                String userInput = storeInputView.getProductsToBuy();
                return purchaseProductFactory.parsePurchaseProduct(userInput);
            } catch (IllegalArgumentException e) {
                storeOutputView.printErrorMessage(e.getMessage());
                getProductsToBuy();
            }
        }
    }

    private PromotionProductGroup getPurchasableProducts(PurchaseProduct purchaseProduct) {
        while (true) {
            try {
                List<Product> sameProductNameStocks = storeStockService.getSameProductNameStocks(purchaseProduct);
                List<Product> checkedProductStock = storeStockService.checkProductStock(purchaseProduct, sameProductNameStocks);
                return storeStockService.separatePromotion(purchaseProduct, checkedProductStock);
            } catch (IllegalArgumentException e) {
                storeOutputView.printErrorMessage(e.getMessage());
                getProductsToBuy();
            }
        }
    }

    private void checkInsufficientBonusPromotionQuantity(PurchaseProduct purchaseProduct, PromotionProductGroup promotionProductGroup) {
        InsufficientBonusProductDto insufficientBonusProductDto = storeStockService.checkInsufficientBonusPromotionQuantity(purchaseProduct, promotionProductGroup);
        if (insufficientBonusProductDto == null) {
            return;
        }
        if (getInsufficientBonusPromotionQuantity(insufficientBonusProductDto)) {
            //TODO : 재고 수량 제거
        }
    }

    private boolean getInsufficientBonusPromotionQuantity(InsufficientBonusProductDto insufficientBonusProductDto) {
        while (true) {
            try {
                String confirmInsufficient = storeInputView.getConfirmInsufficientBonusPromotionProduct(insufficientBonusProductDto);
                return YesNoValidator.validateYN(confirmInsufficient);
            } catch (IllegalArgumentException e) {
                storeOutputView.printErrorMessage(ErrorMessage.ETC_ERROR.getErrorMessage());
            }
        }
    }

    private void checkPromotionQuantityOverStock(PurchaseProduct purchaseProduct, PromotionProductGroup promotionProductGroup) {
        PromotionQuantityOverStockDto promotionQuantityOverStockDto = storeStockService.checkPromotionQuantityOverStock(purchaseProduct, promotionProductGroup);
        if (promotionQuantityOverStockDto == null) {
            return;
        }
        if (getPromotionQuantityOverStock(promotionQuantityOverStockDto)) {
            //TODO : 재고 수량 제거
        }
    }

    private boolean getPromotionQuantityOverStock(PromotionQuantityOverStockDto promotionQuantityOverStockDto) {
        while (true) {
            try {
                String confirmOverStock = storeInputView.getPromotionQuantityOverStock(promotionQuantityOverStockDto);
                return YesNoValidator.validateYN(confirmOverStock);
            } catch (IllegalArgumentException e) {
                storeOutputView.printErrorMessage(ErrorMessage.ETC_ERROR.getErrorMessage());
            }
        }
    }
}
