package domain.model;

import controller.*;

public class FamilyProduct {

    Company company = App.getInstance().getCompany();

    private String productId;
    private int stock;
    private boolean buy;

    public FamilyProduct(String productId, int stock, boolean buy) {
        this.productId = productId;
        this.stock = stock;
        this.buy = buy;
    }

    public String getProductId() {
        return productId;
    }

    public int getStock() {
        return stock;
    }

    public boolean getBuy() {
        return buy;
    }

    public String toString() {
        return company.getProductsName(productId);
    }
}
