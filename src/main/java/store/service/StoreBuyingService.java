package store.service;

import store.model.PurchaseProduct;

import java.util.ArrayList;
import java.util.List;

public class StoreBuyingService {
    public List<PurchaseProduct> getProductsToBuy(String input) {
        List<String> productStrings = splitProducts(input);
        return parseProducts(productStrings);
    }

    private List<String> splitProducts(String input) {
        String[] products = input.split(",");
        List<String> productList = new ArrayList<>();
        for (String product : products) {
            productList.add(removeBrackets(product));
        }
        return productList;
    }

    private String removeBrackets(String product) {
        return product.replace("[", "").replace("]", "");
    }

    private List<PurchaseProduct> parseProducts(List<String> productStrings) {
        List<PurchaseProduct> productsToBuy = new ArrayList<>();
        for (String productString : productStrings) {
            productsToBuy.add(parseProduct(productString));
        }
        return productsToBuy;
    }

    private PurchaseProduct parseProduct(String productString) {
        String[] parts = productString.split("-");
        String name = parts[0];
        String quantity = parts[1];
        return new PurchaseProduct(name, quantity);
    }
}