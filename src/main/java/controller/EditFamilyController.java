package controller;

import domain.model.*;

import java.util.*;

public class EditFamilyController {

    Company company = App.getInstance().getCompany();

    public List<String> getFamilysInfo() {
        return company.getFamilysInfo();
    }

    public boolean validateEmail(String email) {
        if (!company.validateEmailFormat(email))
            return false;

        return company.verifyIfCanBeAdmin(email);
    }

    public void save(String name, String email, String password) {
        if (!name.isEmpty())
            company.updateFamilysName(name);

        if (!email.isEmpty())
            company.updateFamilysEmail(email);

        if (!password.isEmpty())
            company.updateFamilysPassword(password);
    }


    public void leaveFamily() {
        company.leaveFamily();
    }
}
