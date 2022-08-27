package domain.shared;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {



    String url = "jdbc:mysql://urvuo2fsyt5yzsvf:6yGXE6wPXlyR0acC0dyA@bxjpi4ccasdrzjxogrqb-mysql.services.clever-cloud.com:3306/bxjpi4ccasdrzjxogrqb";
    String username = "urvuo2fsyt5yzsvf";
    String password = "6yGXE6wPXlyR0acC0dyA";


    public Connection conn;

    {
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PreparedStatement pst;
    public Statement st;
    public ResultSet rs;



    static Scanner sc = new Scanner(System.in);

    public static int showAndSelectIndex(List<MenuItem> list, String header) {
        showList(list, header);
        return selectsIndex(list);
    }

    public static void showList(List list, String header) {
        title(header);
        System.out.println();
        int index = 0;
        for (Object o : list) {
            index++;

            System.out.println(index + ". " + o.toString());
        }
        System.out.println("");
        System.out.println("0 - Cancel");
    }

    public static int selectsIndex(List list) {
        String input = "";
        int value = -1;
        do {
            try {
                input = Utils.readLineFromConsole("Type your option: ");
                value = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid option");
            }
        } while (value < 0 || value > list.size());

        return value - 1;
    }



    static public String readLineFromConsole(String prompt) {
        System.out.print("\n" + prompt);
        return sc.nextLine();
    }

    public static int readIntegerFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);

                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }



    private static void dash() {
        for (int i = 0; i < 40; i++)
            System.out.print("-");
        System.out.println();
    }

    public static void title(String title) {
        //System.out.printf("%n%n");
        dash();
        System.out.printf("%22s%n", title);
        dash();
    }

}
