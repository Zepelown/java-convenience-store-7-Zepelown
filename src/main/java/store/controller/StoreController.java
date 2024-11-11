package store.controller;

import store.data.repository.StoreProductRepository;
import store.data.repository.StorePromotionRepository;
import store.dto.InsufficientBonusProductDto;
import store.dto.ProductDto;
import store.dto.PromotionQuantityOverStockDto;
import store.exception.ErrorMessage;
import store.model.*;
import store.service.StoreReceiptService;
import store.service.StoreStockService;
import store.util.YesNoValidator;
import store.view.StoreInputView;
import store.view.StoreOutputView;

import java.util.ArrayList;
import java.util.List;

public class StoreController {
    private final StoreProductRepository storeProductRepository;
    private final StorePromotionRepository storePromotionRepository;
    private final StoreInputView storeInputView;
    private final StoreOutputView storeOutputView;
    private final PurchaseProductFactory purchaseProductFactory = new PurchaseProductFactory();
    private final StoreStockService storeStockService;
    private final StoreReceiptService storeReceiptService;

    public StoreController(StoreProductRepository storeProductRepository, StorePromotionRepository storePromotionRepository, StoreInputView storeInputView, StoreOutputView storeOutputView) {
        this.storeProductRepository = storeProductRepository;
        this.storePromotionRepository = storePromotionRepository;
        this.storeInputView = storeInputView;
        this.storeOutputView = storeOutputView;

        storeStockService = new StoreStockService(storeProductRepository, storePromotionRepository);
        storeReceiptService = new StoreReceiptService();
    }

    public void start() {
        storeOutputView.printProductStockNotification();
        List<ProductDto> products = storeStockService.loadProductStock();
        storeOutputView.printProductStock(products);

        List<PurchaseProduct> productsToBuy = getProductsToBuy();

        List<PurchasedProduct> purchasedProducts = new ArrayList<>();
        for (PurchaseProduct purchaseProduct : productsToBuy) {
            purchasedProducts.add(processPurchaseProduct(purchaseProduct));
        }

        finalizePurchasingProduct(purchasedProducts);
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

    private PurchasedProduct processPurchaseProduct(PurchaseProduct purchaseProduct) {
        PromotionProductGroup promotionProductGroup = getPurchasableProducts(purchaseProduct);
        PurchasedProduct purchasedProduct = new PurchasedProduct(purchaseProduct.getName(), purchaseProduct.getQuantity(), 0, 0, promotionProductGroup.getProductCost(), promotionProductGroup.getProductPromotion());

        PurchasedProduct checkedPurchasedProduct = checkInsufficientBonusPromotionQuantity(purchasedProduct, promotionProductGroup);

        checkedPurchasedProduct = checkPromotionQuantityOverStock(checkedPurchasedProduct, promotionProductGroup);


        return storeStockService.buyProduct(checkedPurchasedProduct, promotionProductGroup);
    }

    private void finalizePurchasingProduct(List<PurchasedProduct> purchasedProducts) {
        boolean isMemberShip = getMemberShipConfirm();
        storeReceiptService.calculateReceipt(purchasedProducts,isMemberShip);
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

    private PurchasedProduct checkInsufficientBonusPromotionQuantity(PurchasedProduct purchasedProduct, PromotionProductGroup promotionProductGroup) {
        InsufficientBonusProductDto bonusProductDto = promotionProductGroup.checkInsufficientBonusPromotionQuantity(purchasedProduct);

        if (bonusProductDto == null) {
            return purchasedProduct;
        }

        boolean confirmedAdditionalQuantity = getInsufficientBonusPromotionQuantity(bonusProductDto);
        if (!confirmedAdditionalQuantity) {
            purchasedProduct.addTotalQuantity(bonusProductDto.getQuantity());
        }

        return purchasedProduct;
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

    private PurchasedProduct checkPromotionQuantityOverStock(PurchasedProduct purchasedProduct, PromotionProductGroup promotionProductGroup) {
        PromotionQuantityOverStockDto overStockDto = promotionProductGroup.checkPromotionQuantityOverStock(purchasedProduct);
        if (overStockDto == null) {
            return purchasedProduct;
        }

        boolean confirmAdditionalQuantity = getPromotionQuantityOverStock(overStockDto);
        if (!confirmAdditionalQuantity) {
            purchasedProduct.minusTotalQuantity(overStockDto.getQuantity());
            return purchasedProduct;
        }
        return purchasedProduct;
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

    private boolean getMemberShipConfirm() {
        while (true) {
            try {
                String memberShipConfirm = storeInputView.getMemberShipConfirm();
                return YesNoValidator.validateYN(memberShipConfirm);
            } catch (IllegalArgumentException e) {
                storeOutputView.printErrorMessage(ErrorMessage.ETC_ERROR.getErrorMessage());
            }
        }
    }
}
