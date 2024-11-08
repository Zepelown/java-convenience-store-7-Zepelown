package store.data.repository;

import store.data.entity.Product;
import store.model.PurchaseProduct;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StoreProductRepository implements StoreRepository {
    private final String PRODUCT_FILE_NAME = "products.md";

    private List<Product> productStock;

    public StoreProductRepository() throws IOException {
        productStock = loadProductsFromFile();
    }

    public List<Product> loadProductStock() {
        return productStock;
    }

    public List<Product> findPurchasableProducts(PurchaseProduct purchaseProduct){
        ArrayList<Product> purchasableProducts = new ArrayList<>();
        for (Product stock : productStock){
            if (stock.canPurchase(purchaseProduct)){
                purchasableProducts.add(stock);
            }
        }
        return purchasableProducts;
    }

    private List<Product> loadProductsFromFile() throws IOException {
        try (BufferedReader reader = createBufferedReader(PRODUCT_FILE_NAME)) {
            return reader.lines()
                    .skip(1)
                    .map(this::parseProductLine)
                    .collect(Collectors.toList());
        }
    }

    private Product parseProductLine(String line) {
        String[] parts = line.split(FILE_DELIMITER);
        String name = parts[0];
        String price = parts[1];
        String quantity = parts[2];
        String promotion = null;

        if (parts.length > 3 && !parts[3].isEmpty()) {
            promotion = parts[3];
        }

        return new Product(name, price, quantity, promotion);
    }
}
