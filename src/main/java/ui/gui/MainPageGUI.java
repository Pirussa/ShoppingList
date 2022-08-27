package ui.gui;

import controller.CategoryPageController;
import controller.MainPageController;
import domain.model.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import miscellananeous.*;

import java.io.IOException;
import java.util.*;

public class MainPageGUI  {

    MainPageController controller;


    public void setController(MainPageController controller) {
        this.controller = controller;
    }

    @FXML
    private HBox hBoxCereals, hBoxConfigurations, hBoxDairy, hBoxDrinks, hBoxFrozen, hBoxFruits, hBoxHygiene, hBoxLaundry, hBoxMeat, hBoxOther, hBoxSnacks, hBoxMain;

    @FXML
    private VBox tab;

    @FXML
    private TableView<MainPageTableView> tbProducts;

    @FXML
    private TableColumn<MainPageTableView, Button> colCheckbox;

    @FXML
    private TableColumn<MainPageTableView, String> colName;

    @FXML
    private TableColumn<MainPageTableView, Integer> colQuantiy;

    ObservableList<MainPageTableView> statsList;

    @FXML
    private HBox mainHBox;

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


    @FXML
    void clearChecks(ActionEvent event) {
        List<FamilyProduct> productList = controller.getFamilyProductsChecked();

        for (MainPageTableView object : statsList)
            if(object.getBuy().isSelected())
               //checkedProducts.add(object);
                System.out.println();

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
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        setStatsTable();
        System.out.println(colCheckbox.getCellFactory().toString());
    }


    public void setStatsTable() {
        mainHBox.setStyle("-fx-background-color: #ffd4bd");

        List<FamilyProduct> productList = controller.getFamilyProductsChecked();
        statsList = FXCollections.observableArrayList();

        for (FamilyProduct product : productList)
            statsList.add(new MainPageTableView(product));


        colCheckbox.setCellValueFactory(new PropertyValueFactory<>( "buy" ));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQuantiy.setCellValueFactory(new PropertyValueFactory<>("stock"));

        ObservableList<MainPageTableView> list;
        if(productList.isEmpty()) {
            list = FXCollections.observableArrayList(
                    new MainPageTableView(new FamilyProduct("1", 0, false))
            );
            tbProducts.setItems(list);
        }
        else
            tbProducts.setItems(statsList);

    }

}