package miscellananeous;

import javafx.beans.property.*;
import javafx.scene.control.*;

public class MainPageTableView {

    private final SimpleStringProperty name;
    private final SimpleIntegerProperty stock;
    private CheckBox buy;

    public MainPageTableView(String name, int stock) {
        this.name = new SimpleStringProperty(name);
        this.stock = new SimpleIntegerProperty(stock);
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
