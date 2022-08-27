package ui.gui;

import com.jfoenix.controls.*;
import controller.RegisterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterGUI {

    RegisterController controller = new RegisterController();

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private JFXPasswordField txtPin1;

    @FXML
    private JFXPasswordField txtPin2;

    @FXML
    void register(ActionEvent event) throws IOException {
        if (validateRegistration(txtEmail.getText(), txtName.getText(), txtPin1.getText(), txtPin2.getText())) {
            controller.registerUser(txtEmail.getText(), txtName.getText(), Integer.parseInt(txtPin1.getText()));
            controller.toFamilyPage(event);
        }
    }

    public boolean validateRegistration(String email, String name, String pin, String pinConfirmation) {
        Alert alert;

        if(!controller.validateEmail(email)) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid email");
            alert.setContentText("The introduced email is invalid.");
            alert.showAndWait();
            return false;
        }


        if(!controller.validateName(name)) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Name");
            alert.setContentText("The name mustn't contain any number");
            alert.showAndWait();
            return false;
        }


        if(!controller.validatePin(pin, pinConfirmation)) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid pin");
            alert.setContentText("The pin must be a four digit number");
            alert.showAndWait();
            return false;
        }

        return true;
    }


    public void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/auth.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}