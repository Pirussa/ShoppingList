package domain.shared;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Constants {
    String url = "jdbc:mysql://127.0.0.1:3306/AppDB";
    String username = "root";
    String password = "N#Q$StySAtMMDT95";

    public static Connection conn;

    {
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement pst;
}
