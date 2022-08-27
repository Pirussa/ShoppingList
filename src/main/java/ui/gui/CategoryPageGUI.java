package ui.gui;

import controller.*;
import domain.model.*;
import javafx.animation.TranslateTransition;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import miscellananeous.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryPageGUI {

    private CategoryPageController controller;

    public void setController(CategoryPageController controller) {
        this.controller = controller;
    }

    private String categoryName;

    @FXML
    private TableView<CategoryPageTableView> tbProducts;

    @FXML
    private TableColumn<CategoryPageTableView, CheckBox> colCheckbox;

    @FXML
    private TableColumn<CategoryPageTableView, String> colName;

    @FXML
    private TableColumn<CategoryPageTableView, Integer> colQuantiy;

    @FXML
    private Label lbTitle;

    @FXML
    private VBox tab;

    @FXML
    private HBox hBoxCereals, hBoxConfigurations, hBoxDairy, hBoxDrinks, hBoxFrozen, hBoxFruits, hBoxHygiene, hBoxLaundry, hBoxMeat, hBoxOther, hBoxSnacks, hBoxMain;



    @FXML
    void addProduct(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addProduct.fxml"));
        root = loader.load();

        AddProductGUI mainScene = loader.getController();
        mainScene.setController(new AddProductController());
        mainScene.setStatsTable();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void createProduct(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/createProduct.fxml"));
        root = loader.load();

        CreateProductGUI mainScene = loader.getController();
        mainScene.setController(new CreateProductController());
        mainScene.setStatsTable();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




    @FXML
    void closeTab(ActionEvent event) {
        tab.setVisible(false);
    }

    @FXML
    void openTab(ActionEvent event) {
        tab.setVisible(true);
    }




    @FXML
    void toMainPage(ActionEvent event) throws IOException {
        toMainPageGUI(event);
    }

    @FXML
    void toHygiene(ActionEvent event) throws IOException {
        toCategoryPageGUI("Hygiene", event, 6);
    }

    @FXML
    void toCerealsBake(ActionEvent event) throws IOException {
        toCategoryPageGUI("Cereal and Bakery", event, 0);
    }

    @FXML
    void toDairy(ActionEvent event) throws IOException {
        toCategoryPageGUI("Dairy", event, 1);
    }

    @FXML
    void toDrinks(ActionEvent event) throws IOException {
        toCategoryPageGUI("Drinks", event, 2);
    }

    @FXML
    void toFrozen(ActionEvent event) throws IOException {
        toCategoryPageGUI("Frozen", event, 3);
    }

    @FXML
    void toFruitsAndVeggies(ActionEvent event) throws IOException {
        toCategoryPageGUI("Fruits and Veggies", event, 4);
    }

    @FXML
    void toLaundry(ActionEvent event) throws IOException {
        toCategoryPageGUI("Laundry", event, 5);
    }

    @FXML
    void toMeatFish(ActionEvent event) throws IOException {
        toCategoryPageGUI("Meat and Fish", event, 7);
    }

    @FXML
    void toOther(ActionEvent event) throws IOException {
        toCategoryPageGUI("Other", event, 9);
    }

    @FXML
    void toSnacks(ActionEvent event) throws IOException {
        toCategoryPageGUI("Snacks", event, 8);
    }

    public void toConfigurations(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/configurationsPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void setStatsTable(String categoryName, int identifier) {
        lbTitle.setText(categoryName);
        checkTab(identifier);

        List<FamilyProduct> productList = controller.getFamilyProductsByCategory(categoryName);
        ObservableList<CategoryPageTableView> statsList = FXCollections.observableArrayList();

        for (FamilyProduct product : productList)
            statsList.add(new CategoryPageTableView(product.toString(), product.getStock()));

        colCheckbox.setCellValueFactory(new PropertyValueFactory<>( "buy" ));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQuantiy.setCellValueFactory(new PropertyValueFactory<>("stock"));

        ObservableList<CategoryPageTableView> list;
        if(productList.isEmpty()) {
            list = FXCollections.observableArrayList(
                    new CategoryPageTableView("No products needed to buy", 0)
            );
            tbProducts.setItems(list);
        }
        else
            tbProducts.setItems(statsList);

    }

    private void checkTab(int identifier) {
        HBox[] tabNames = {
                hBoxCereals,
                hBoxDairy,
                hBoxDrinks,
                hBoxFrozen,
                hBoxFruits,
                hBoxLaundry,
                hBoxHygiene,
                hBoxMeat,
                hBoxSnacks,
                hBoxOther};

        tabNames[identifier].setStyle("-fx-background-color: #ffd4bd");
    }

    private void toCategoryPageGUI(String title, ActionEvent event, int identifier) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/categoryPage.fxml"));
        root = loader.load();

        CategoryPageGUI mainScene = loader.getController();
        mainScene.setController(new CategoryPageController());
        mainScene.setStatsTable(title, identifier);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void toMainPageGUI(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainPage.fxml"));
        root = loader.load();

        MainPageGUI mainScene = loader.getController();
        mainScene.setController(new MainPageController());
        mainScene.setStatsTable();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}