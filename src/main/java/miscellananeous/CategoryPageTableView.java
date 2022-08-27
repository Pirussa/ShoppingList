package miscellananeous;

import controller.*;
import domain.model.*;
import javafx.beans.property.*;
import javafx.scene.control.*;

public class CategoryPageTableView {

    CategoryPageController controller;

    private final SimpleStringProperty name;
    private final SimpleIntegerProperty stock;
    private CheckBox buy;

    public CategoryPageTableView(FamilyProduct product) {
        this.name = new SimpleStringProperty(product.toString());
        this.stock = new SimpleIntegerProperty(product.getStock());
        this.buy = new CheckBox();
        controller = new CategoryPageController();

        buy.setSelected(product.getBuy());

        buy.setOnAction(tc -> {
            if (buy.isSelected())
                controller.checkBuyOption(product, 1);
            else
                controller.checkBuyOption(product, 0);

        });
    }


    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getStock() {
        return stock.get();
    }

    public SimpleIntegerProperty stockProperty() {
        return stock;
    }

    public CheckBox getBuy() {
        return buy;
    }

    public void setBuy(CheckBox buy) {
        this.buy = buy;
    }
}
