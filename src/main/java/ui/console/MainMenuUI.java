package ui.console;


import domain.shared.MenuItem;
import domain.shared.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainMenuUI implements Runnable {


    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<>();
        options.add(new MenuItem("Register", new RegisterUI()));
        options.add(new MenuItem("Login", new LoginUI()));
        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "Main Menu");
            if ((option >= 0) && (option < options.size())) {
                System.out.printf("%n%n");
                options.get(option).run();
            }
        }
        while (option != -1);

    }
}
