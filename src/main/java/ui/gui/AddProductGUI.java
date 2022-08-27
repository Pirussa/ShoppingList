package ui.gui;

import com.jfoenix.controls.*;
import controller.AddProductController;
import domain.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AddProductGUI {

    AddProductController controller;

    public void setController(AddProductController controller) {
        this.controller = controller;
    }

    @FXML
    private ChoiceBox<String> cBoxCategories;

    @FXML
    private ChoiceBox<Product> cBoxProducts;

    @FXML
    private VBox filterTab;

    @FXML
    private TextField txtName;

    @FXML
    private JFXTextField txtStock;

    @FXML
    void add(ActionEvent event) {
        if (!controller.checkIfIsInteger(txtStock.getText()))
            controller.addProduct(cBoxProducts.getValue(), Integer.parseInt(txtStock.getText()));

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Product added");
        alert.setContentText(cBoxProducts.getValue() + " was sucessefully added.");
        alert.showAndWait();
    }

    @FXML
    void openAndCloseTab(ActionEvent event) {
        filterTab.setVisible(!filterTab.isVisible());
    }

    @FXML
    void filter(ActionEvent event) {
        List<Product> filteredList = controller.filter(txtName.getText(), cBoxCategories.getValue());
        ObservableList<Product> options = FXCollections.observableArrayList(filteredList);
        cBoxProducts.setItems(options);

        filterTab.setVisible(false);
    }

    public void setStatsTable() {
        List<Product> productsName = controller.getProducts();
        ObservableList<Product> options = FXCollections.observableArrayList(productsName);
        cBoxProducts.setItems(options);

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

        filterTab.setVisible(false);
    }

    public void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
