package ui.console;

import controller.JoinFamilyController;
import domain.shared.Utils;

import java.awt.event.ActionEvent;

public class JoinFamilyUI implements Runnable {

    JoinFamilyController controller = new JoinFamilyController();

    @Override
    public void run() {
        Utils.title("Join Family");

        String email = Utils.readLineFromConsole("Email form the admin: ");

        if(controller.validateInsertedEmail(email) != null) {
            controller.joinFamily(email);
        }
    }
}
