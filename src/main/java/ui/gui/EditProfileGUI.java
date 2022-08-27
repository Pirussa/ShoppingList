package ui.gui;

import controller.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class EditProfileGUI implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initial();
    }


    EditProfileController controller = new EditProfileController();

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPin;

    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/configurationsPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void save(ActionEvent event) {
        Alert alert;

        if (!txtName.getText().isEmpty() && !controller.validateName(txtName.getText())) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText("Error changing the name");
            alert.setContentText("Invalid name. It musn't contain numbers");
            alert.showAndWait();
        }

        if(!txtEmail.getText().isEmpty() && !controller.validateEmail(txtEmail.getText())) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setTitle("Error changing the email");
            alert.setContentText("Invalid email");
            alert.showAndWait();
        }

        if(!txtPin.getText().isEmpty() && !controller.validatePin(txtPin.getText())) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setTitle("Error changing the pin");
            alert.setContentText("The pin must be a four digit number.");
            alert.showAndWait();
        }

        else {
            controller.save(txtName.getText(), txtEmail.getText(), txtPin.getText());
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Alert");
            alert.setHeaderText("Update Completed");
            alert.setContentText("Your changes were successully saved");
            alert.showAndWait();
            initial();
        }

    }

    private void initial() {
        txtName.setText("");
        txtEmail.setText("");
        txtPin.setText("");
        List<String> usersInfo = controller.getUsersInfo();
        txtName.setPromptText(usersInfo.get(0));
        txtEmail.setPromptText(usersInfo.get(1));
    }


}
