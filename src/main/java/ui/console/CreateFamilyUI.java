package ui.console;

import controller.CreateFamilyController;
import domain.shared.Utils;

public class CreateFamilyUI implements Runnable {

    CreateFamilyController controller = new CreateFamilyController();

    @Override
    public void run() {
        Utils.title("Create Family");

        String name = Utils.readLineFromConsole("Family's name: ");

        if(controller.validateName(name))
            System.out.println();
            //controller.createFamily(name);
    }
}
