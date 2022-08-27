package ui.gui;

import com.jfoenix.controls.JFXTextField;
import controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginGUI implements Initializable  {

    LoginController controller = new LoginController();


    @FXML
    private Button btLogin;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private TextField txtPin;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1){

        Font montserrat = null;

        try {
            montserrat = Font.loadFont(new FileInputStream("src/main/resources/Fonts/Montserrat Regular.ttf"), 17);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        btLogin.setFont(montserrat);
    }


    @FXML
    void login(ActionEvent event) throws IOException {

        Alert alert;
        if (controller.checkIfIsInteger(txtPin.getText())) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Pin");
            alert.setContentText("Only numbers are allowed on the PIN");
            alert.showAndWait();
        }
        else if (!controller.validateLogin(txtEmail.getText(), Integer.parseInt(txtPin.getText()))) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login failed");
            alert.setContentText("Incorrect email or pin.");
            alert.showAndWait();
        }
        else {
            controller.setUser();
            if(!controller.hasFamily())
                controller.toFamilyPage(event);

            else
                controller.toMainPage(event);
        }

    }

    public void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/auth.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}