package ui.gui;

import com.jfoenix.controls.*;
import controller.CreateFamilyController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateFamilyGUI {

    CreateFamilyController controller = new CreateFamilyController();

    @FXML
    private TextField txtName;

    @FXML
    private JFXPasswordField txtPass1;

    @FXML
    private JFXPasswordField txtPass2;

    @FXML
    void createFamily(ActionEvent event) throws IOException {
        if(controller.validatePasswords(txtPass1.getText(), txtPass2.getText())) {
            controller.createFamily(txtName.getText(), txtPass1.getText());
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
