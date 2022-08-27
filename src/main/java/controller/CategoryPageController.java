package controller;

import domain.model.*;

import java.util.List;

public class CategoryPageController {

    private final Company company;

    /**
     * Instantiates a new Check and export vaccination stats controller.
     */
    public CategoryPageController() {
        company = App.getInstance().getCompany();
    }



    public List<FamilyProduct> getFamilyProductsByCategory(String categoryName) {
        return company.getFamilyProductByCategory(categoryName);
    }
}





