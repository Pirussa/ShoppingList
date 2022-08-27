package controller;

import domain.model.Company;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    Company company = App.getInstance().getCompany();




    private boolean emailExitsOnTheDB(String email) {
        return company.emailExitsOnTheDB(email);
    }

    private boolean validateEmailFormat(String email) {
        return company.validateEmailFormat(email);
    }

    public boolean validateEmail(String email) {
        if(emailExitsOnTheDB(email))
            return false;

        return validateEmailFormat(email);
    }



    public boolean validateName(String name) {
        return company.validateName(name);
    }

    private boolean pinMatch(int pin, int pinConfirmation) {
        return company.pinMatch(pin, pinConfirmation);
    }

    private boolean fourDigitPinCheck(int pin) {
        return company.fourDigitPinCheck(pin);
    }

    private boolean checkEmpty(String pin, String pinConfirmation) {
        return company.checkEmpty(pin, pinConfirmation);
    }

    private boolean checkIfIsInteger(String pin) {
        return company.checkIfIsInteger(pin);
    }

    public boolean validatePin(String pin, String pinConfirmation) {
        if(checkEmpty(pin, pinConfirmation))
            return false;

        if(checkIfIsInteger(pin))
            return false;

        if (!pinMatch(Integer.parseInt(pin), Integer.parseInt(pinConfirmation)))
            return false;

        return fourDigitPinCheck(Integer.parseInt(pin));
    }

    public void registerUser(String email, String name, int pin) {
        company.registerUser(email, name, pin);
    }


    public void toFamilyPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/family.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
