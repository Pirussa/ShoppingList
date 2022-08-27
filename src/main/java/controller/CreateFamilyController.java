package controller;

import domain.model.Company;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateFamilyController {

    Company company = App.getInstance().getCompany();

    public void createFamily(String name, String password) {
         company.createFamily(name, password);
    }

    public boolean validateName(String name) {
        return company.validateName(name);
    }

    public void toMainPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public boolean validatePasswords(String pass1, String pass2) {
        return pass1.equals(pass2);
    }
}
