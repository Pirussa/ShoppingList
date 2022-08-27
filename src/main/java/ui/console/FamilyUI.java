package ui.console;

import domain.shared.MenuItem;
import domain.shared.Utils;

import java.util.ArrayList;
import java.util.List;

public class FamilyUI implements Runnable {

    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<>();
        options.add(new MenuItem("Create Family", new CreateFamilyUI()));
        options.add(new MenuItem("Join Family", new JoinFamilyUI()));
        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "Family Union");
            if ((option >= 0) && (option < options.size())) {
                System.out.printf("%n%n");
                options.get(option).run();
            }
        }
        while (option != -1);
    }
}
