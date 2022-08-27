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



    public boolean validateEmail(String email) {
        return company.validateEmail(email);
    }


    public boolean validateName(String name) {
        return company.validateName(name);
    }


    public boolean validatePin(String pin, String pinConfirmation) {
        if(company.checkEmpty(pin, pinConfirmation))
            return false;

        if(company.checkIfIsInteger(pin))
            return false;

        if (!company.pinMatch(Integer.parseInt(pin), Integer.parseInt(pinConfirmation)))
            return false;

        return company.fourDigitPinCheck(Integer.parseInt(pin));
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
