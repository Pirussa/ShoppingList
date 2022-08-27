package ui.console;

import controller.RegisterController;
import domain.shared.Utils;


public class RegisterUI implements Runnable {

    RegisterController controller = new RegisterController();

    @Override
    public void run() {
        Utils.title("Register");

        String email = Utils.readLineFromConsole("Email: ");
        String name = Utils.readLineFromConsole("Name: ");
        int pin = Utils.readIntegerFromConsole("PIN: ");
        int pinConfirmation = Utils.readIntegerFromConsole("PIN again: ");

        if (validateRegistration(email, name, String.valueOf(pin), String.valueOf(pinConfirmation))) {
            controller.registerUser(email, name, pin);
            System.out.printf("%nRegistration successful%n");
            new FamilyUI().run();
        }
        else
            System.out.println("Requirements not meet. Account not created");
    }

    public boolean validateRegistration(String email, String name, String pin, String pinConfirmation) {

        if(!controller.validateEmail(email))
            return false;

        if(!controller.validateName(name))
            return false;

        return controller.validatePin(pin, pinConfirmation);
    }

}



