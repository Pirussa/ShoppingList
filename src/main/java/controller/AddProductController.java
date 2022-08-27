package controller;

import domain.model.*;

import java.util.List;

public class AddProductController {

    Company company = App.getInstance().getCompany();
    public List<Product> getProducts() {
        return company.getProducts();
    }

    public List<Product> filter(String txtName, String cBoxValue) {
        return company.filter(txtName, cBoxValue);
    }

    public void addProduct(Product product, int stock) {
        company.addProduct(product, stock);
    }

    public boolean checkIfIsInteger(String txtStock) {
        return company.checkIfIsInteger(txtStock);
    }
}
