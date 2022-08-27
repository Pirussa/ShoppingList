package controller;

import domain.model.Company;

public class CreateProductController {

    Company company = App.getInstance().getCompany();

    public boolean checkIfEmpty(String cBoxValue, String txtName, String txtPrice) {
        return cBoxValue == null || txtName.equals("") || txtPrice.equals("");
    }

    public boolean validatePrice(String price) {
        return price.matches("\\d*");
    }

    public void createProduct(String cBoxValue, String txtName, String txtPrice) {
        company.createProduct(cBoxValue, txtName, txtPrice);
    }
}
