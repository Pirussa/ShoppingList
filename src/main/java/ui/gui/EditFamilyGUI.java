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

public class EditFamilyGUI implements Initializable {

    EditFamilyController controller = new EditFamilyController();

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initial();
    }

    @FXML
    void save(ActionEvent event) {
        Alert alert;

        if(!txtEmail.getText().isEmpty() && !controller.validateEmail(txtEmail.getText())) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setTitle("Error changing the email");
            alert.setContentText("The user must have an account and not be Admin of another family.");
            alert.showAndWait();
        }

        else {
            controller.save(txtName.getText(), txtEmail.getText(), txtPin.getText());
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Alert");
            alert.setHeaderText("Update Completed");
            alert.setContentText("Your changes were successully saved");
            alert.showAndWait();
            initial();
        }
    }

    @FXML
    void leaveFamily(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning Alert");
        alert.setHeaderText("You are about to leave this family");
        alert.setContentText("Are you sure you want to leave the family?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK) {
            controller.leaveFamily();

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/family.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/configurationsPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void initial() {
        txtName.setText("");
        txtEmail.setText("");
        txtPin.setText("");
        List<String> usersInfo = controller.getFamilysInfo();
        txtName.setPromptText(usersInfo.get(0));
        txtEmail.setPromptText(usersInfo.get(1));
    }
}
