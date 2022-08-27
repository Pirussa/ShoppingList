package domain.model;

import domain.shared.Utils;

import java.sql.SQLException;

public class User {

    Utils utils = new Utils();

    private int id;
    private String email;
    private String name;
    private int pin;
    private String familyId;

    public User(String email, String name, int pin) {
        this.email = email;
        this.name = name;
        this.pin = pin;
        id = getUsersId(email);
    }

    private int getUsersId(String email) {
        try {
            utils.pst = utils.conn.prepareStatement("SELECT Id FROM users WHERE email = '" + email + "'");
            utils.rs = utils.pst.executeQuery();

            if (utils.rs.next())
                return Integer.parseInt(utils.rs.getString(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

// put here all the validations
}
