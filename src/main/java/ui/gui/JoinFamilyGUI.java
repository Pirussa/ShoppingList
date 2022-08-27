package ui.gui;

import com.jfoenix.controls.*;
import controller.JoinFamilyController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class JoinFamilyGUI {

    JoinFamilyController controller = new JoinFamilyController();

    @FXML
    private TextField txtEmail;

    @FXML
    private JFXPasswordField txtPass;

    @FXML
    void joinFamily(ActionEvent event) throws IOException {
        if(controller.validateInsertedEmail(txtEmail.getText()) != null && controller.passwordCheck(txtPass.getText(), txtEmail.getText())) {
            controller.joinFamily(txtEmail.getText());
            controller.toMainPage(event);
        }
    }

    public void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/family.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
