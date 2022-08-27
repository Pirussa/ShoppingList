package ui.gui;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthGUI implements Initializable {




    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        try {
            Font.loadFont(new FileInputStream("src/main/resources/Fonts/Catalish Huntera.ttf"), 25);
            Font.loadFont(new FileInputStream("src/main/resources/Fonts/Montserrat ExtraBold.ttf"), 25);
            Font.loadFont(new FileInputStream("src/main/resources/Fonts/Montserrat Regular.ttf"), 18);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void login(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void register(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}