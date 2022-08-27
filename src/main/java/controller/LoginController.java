package controller;

import domain.model.Company;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.gui.MainPageGUI;

import java.io.IOException;

public class LoginController {

    Company company = App.getInstance().getCompany();

    String email;
    int pin;

    public boolean validateLogin(String email, int pin) {
        this.email = email;
        this.pin = pin;
        return company.validateLogin(email, pin);
    }

    public boolean checkIfIsInteger(String pin) {
        return company.checkIfIsInteger(pin);
    }

    public boolean hasFamily() {
        return company.hasFamily(email);
    }


    public void setUser() {
        company.setUser(email, pin);
    }




    public void toFamilyPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/family.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void toMainPage(ActionEvent event) throws IOException {
        company.setFamilyAndUser();

        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainPage.fxml"));
        root = loader.load();

        MainPageGUI mainScene = loader.getController();
        mainScene.setController(new MainPageController());
        mainScene.setStatsTable();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
