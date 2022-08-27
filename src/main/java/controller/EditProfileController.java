package controller;

import domain.model.*;

import java.util.*;

public class EditProfileController {

    Company company = App.getInstance().getCompany();

    public List<String> getUsersInfo() {
        return company.getUsersInfo();
    }

    public boolean validateName(String name) {
        return company.validateName(name);
    }

    public boolean validateEmail(String email) {
        if(!company.verifyIfCanBeAdmin(email))
            return false;

        return company.validateEmailFormat(email);
    }

    public boolean validatePin(String pin) {
        if (company.checkIfIsInteger(pin))
            return false;

        return company.fourDigitPinCheck(Integer.parseInt(pin));
    }

    public void save(String name, String email, String pin) {
        if (!name.isEmpty())
            company.updateName(name);

        if (!email.isEmpty())
            company.updateEmail(email);

        if (!pin.isEmpty())
            company.updatePin(Integer.parseInt(pin));
    }
}
