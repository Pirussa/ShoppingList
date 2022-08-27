package ui.gui;

import controller.CreateProductController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateProductGUI {

    CreateProductController controller;

    public void setController(CreateProductController controller) {
        this.controller = controller;
    }

    @FXML
    private ChoiceBox<String> cBoxCategories;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;


    @FXML
    void create(ActionEvent event) throws IOException {
        Alert alert;
        if(controller.checkIfEmpty(cBoxCategories.getValue(), txtName.getText(), txtPrice.getText())) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Creation failed");
            alert.setContentText("No field can be left empty.");
            alert.showAndWait();
        }
        else if (controller.validatePrice(txtPrice.getText())) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Creation failed");
            alert.setContentText("Price can only contain numbers.");
            alert.showAndWait();
        }
        else {
            controller.createProduct(cBoxCategories.getValue(), txtName.getText(), txtPrice.getText());

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product was created");
            alert.setContentText(txtName.getText() + " was successfully added to the products.");
            alert.showAndWait();

            toMainPage(event);
        }

    }

    private void toMainPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setStatsTable() {
        ObservableList<String> categories = FXCollections.observableArrayList(
                "Cereals and Bakery",
                "Dairy",
                "Drinks",
                "Frozen",
                "Fruits and Veggies",
                "Hygiene",
                "Laundry",
                "Meat and Fish",
                "Other",
                "Snacks");

        cBoxCategories.setItems(categories);
    }

    public void back(ActionEvent actionEvent) throws IOException {
        toMainPage(actionEvent);
    }
}