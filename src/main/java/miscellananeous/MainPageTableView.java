package miscellananeous;

import domain.model.*;
import javafx.beans.property.*;
import javafx.scene.control.*;

public class MainPageTableView {

    private final SimpleStringProperty name;
    private final SimpleIntegerProperty stock;
    private CheckBox buy;

    public MainPageTableView(FamilyProduct product) {
        this.name = new SimpleStringProperty(product.toString());
        this.stock = new SimpleIntegerProperty(product.getStock());
        this.buy = new CheckBox();

        buy.setOnAction(tc -> {
            if (buy.isSelected())
                System.out.println("hello");
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
