package store.controller;

import store.data.repository.StoreProductRepository;
import store.data.repository.StorePromotionRepository;
import store.dto.InsufficientBonusProductDto;
import store.dto.ProductDto;
import store.dto.PromotionQuantityOverStockDto;
import store.exception.ErrorMessage;
import store.model.Product;
import store.model.PromotionProductGroup;
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
            processPurchaseProduct(purchaseProduct);
        }

    }

    private List<PurchaseProduct> getProductsToBuy() {
        while (true) {
            try {
                String userInput = storeInputView.getProductsToBuy();
                return purchaseProductFactory.parsePurchaseProduct(userInput);
            } catch (IllegalArgumentException e) {
                storeOutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void processPurchaseProduct(PurchaseProduct purchaseProduct) {
        PromotionProductGroup promotionProductGroup = getPurchasableProducts(purchaseProduct);

        PurchaseProduct checkedPurchaseProduct = checkInsufficientBonusPromotionQuantity(purchaseProduct, promotionProductGroup);

        checkedPurchaseProduct = checkPromotionQuantityOverStock(checkedPurchaseProduct, promotionProductGroup);

        finalizePurchaseProduct(checkedPurchaseProduct,promotionProductGroup);
    }

    private void finalizePurchaseProduct(PurchaseProduct purchaseProduct, PromotionProductGroup promotionProductGroup) {
        storeStockService.buyProduct(purchaseProduct,promotionProductGroup);
    }

    private PromotionProductGroup getPurchasableProducts(PurchaseProduct purchaseProduct) {
        while (true) {
            try {
                List<Product> sameProductNameStocks = storeStockService.getSameProductNameStocks(purchaseProduct);
                PromotionProductGroup promotionProductGroup = storeStockService.separatePromotion(sameProductNameStocks);
                storeStockService.checkProductStock(purchaseProduct, promotionProductGroup);
                return promotionProductGroup;
            } catch (IllegalArgumentException e) {
                storeOutputView.printErrorMessage(e.getMessage());
                getProductsToBuy();
            }
        }
    }

    private PurchaseProduct checkInsufficientBonusPromotionQuantity(PurchaseProduct purchaseProduct, PromotionProductGroup promotionProductGroup) {
        InsufficientBonusProductDto bonusProductDto = promotionProductGroup.checkInsufficientBonusPromotionQuantity(purchaseProduct);

        if (bonusProductDto == null) {
            return purchaseProduct;
        }

        boolean confirmedAdditionalQuantity = getInsufficientBonusPromotionQuantity(bonusProductDto);
        if (confirmedAdditionalQuantity) {
            purchaseProduct.addQuantity(bonusProductDto.getQuantity());
        }

        return purchaseProduct;
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

    private PurchaseProduct checkPromotionQuantityOverStock(PurchaseProduct purchaseProduct, PromotionProductGroup promotionProductGroup) {
        PromotionQuantityOverStockDto overStockDto = promotionProductGroup.checkPromotionQuantityOverStock(purchaseProduct);
        if (overStockDto == null) {
            return purchaseProduct;
        }

        boolean confirmAdditionalQuantity = getPromotionQuantityOverStock(overStockDto);
        if (!confirmAdditionalQuantity) {
            purchaseProduct.minusQuantity(overStockDto.getQuantity());
        }

        return purchaseProduct;
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
