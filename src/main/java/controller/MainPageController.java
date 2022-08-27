package controller;

import domain.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.gui.CategoryPageGUI;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class MainPageController {

    private final Company company;

    /**
     * Instantiates a new Check and export vaccination stats controller.
     */
    public MainPageController() {
        company = App.getInstance().getCompany();
    }



    public List<FamilyProduct> getFamilyProductsChecked() {
        return company.getFamilyProductChecked();
    }

    public void checkProduct() throws SQLException {
        company.checkProduct();
    }
}
