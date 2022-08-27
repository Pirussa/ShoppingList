package ui.gui;

import controller.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.*;

import java.io.*;

public class EditProfileGUI {

    EditProfileController controller = new EditProfileController();

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPin;

    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clearEmail(ActionEvent event) {

    }

    @FXML
    void clearName(ActionEvent event) {

    }

    @FXML
    void clearPin(ActionEvent event) {

    }

    @FXML
    void familyOptions(ActionEvent event) {

    }

    @FXML
    void save(ActionEvent event) {

    }

}
