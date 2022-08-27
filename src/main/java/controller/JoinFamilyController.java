package controller;

import domain.model.Company;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.gui.*;

import java.io.IOException;

public class JoinFamilyController {

    Company company = App.getInstance().getCompany();

    public String validateInsertedEmail(String email) {
        return company.validateInsertedEmail(email);
    }

    public void joinFamily(String adminEmail) {
        company.joinFamily(adminEmail);
    }



    public void toMainPage(ActionEvent event) throws IOException {
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

    public boolean passwordCheck(String password, String email) {
        return company.passwordCheck(password, email);
    }
}
