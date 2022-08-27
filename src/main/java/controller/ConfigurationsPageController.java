package controller;

import domain.model.*;

public class ConfigurationsPageController {

    Company company = App.getInstance().getCompany();


    public void deleteAccount() {
        company.deleteAccount();
    }
}
