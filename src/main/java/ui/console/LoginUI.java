package ui.console;

import controller.LoginController;
import domain.shared.Utils;

public class LoginUI implements Runnable {

    LoginController controller = new LoginController();

    @Override
    public void run() {
        Utils.title("Login");

        String email = Utils.readLineFromConsole("Email: ");
        int pin = Utils.readIntegerFromConsole("PIN: ");
        System.out.printf("%n%n");

        if (controller.validateLogin(email, pin)) {
            if(!controller.hasFamily()) {
                controller.setUser();
                new FamilyUI().run();
            }
            else
                System.out.println("Ligar à página da família");
        }
        // Verificar se o user apresenta um familyId null. Se assim for apresentar a page do create/join family.
        // Senão apresentar a página com a sua family
    }


}
