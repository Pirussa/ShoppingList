package domain.model;

import domain.shared.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Company {

    Utils utils = new Utils();
    User user;
    Family family;

    public void createFamily(String name, String password) {

        int adminId = user.getId();
        int members = 1;
        setFamily(name, adminId);
        String familyId = family.getId();
        try {
            utils.pst = utils.conn.prepareStatement("INSERT INTO family(id, name, members, adminId, password) VALUES('" + familyId + "', '" + name + "', '" + members + "', '" + adminId + "', '" + password + "')");
            utils.pst.executeUpdate();


            utils.pst = utils.conn.prepareStatement("UPDATE users SET FamilyId = '" + familyId + "' WHERE id = '" + adminId + "'");
            utils.pst.executeUpdate();


            String createTable = "CREATE TABLE " + familyId +
                    "(productId VARCHAR(10) PRIMARY KEY," +
                    " stock INT(3) NOT NULL," +
                    " buy INT(1) NOT NULL," +
                    " FOREIGN KEY (productID) REFERENCES product(id) ON DELETE CASCADE)";

            utils.st = utils.conn.createStatement();
            utils.st.executeUpdate(createTable);
            utils.st.close();

            user.setFamilyId(familyId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void joinFamily(String adminEmail) {
        int members = 0;

        try {
            utils.pst = utils.conn.prepareStatement("SELECT Members FROM family WHERE Id = '" + getUsersFamilyId(adminEmail) + "'");
            utils.rs = utils.pst.executeQuery();

            if (utils.rs.next())
                members = Integer.parseInt(utils.rs.getString(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        members++;
        String familyId = getUsersFamilyId(adminEmail);

        try {
            utils.pst = utils.conn.prepareStatement("UPDATE users SET FamilyId = '" + familyId + "' WHERE email = '" + user.getEmail() + "'");
            utils.pst.executeUpdate();

            utils.pst = utils.conn.prepareStatement("UPDATE family SET Members = '" + members + "' WHERE id = '" + getUsersFamilyId(adminEmail) + "'");
            utils.pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        user.setFamilyId(familyId);
        setFamilyAndUser();
    }

    public void setFamily(String name, int adminId) {
        family = new Family(name, adminId);
    }


    public void setFamilyAndUser() {
        user.setFamilyId(getUsersFamilyId());
        family = new Family(getFamilyAdminId(user.getFamilyId()));
    }

    private int getFamilyAdminId(String familyId) {
        try {
            utils.pst = utils.conn.prepareStatement("SELECT adminId FROM family WHERE Id = '" + familyId + "'");
            utils.rs = utils.pst.executeQuery();

            if (utils.rs.next())
                return Integer.parseInt(utils.rs.getString(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    private String getFamilyAdminEmail(int adminId) {
        try {
            String query = "SELECT email FROM users WHERE id = '" + adminId + "'";
            utils.pst = utils.conn.prepareStatement(query);
            utils.rs = utils.pst.executeQuery();

            if (utils.rs.next())
                return utils.rs.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private String getFamilyStringInfo(String familyId, String info) {
        try {
            String query = "SELECT " + info + " FROM family WHERE id = '" + familyId + "'";
            utils.pst = utils.conn.prepareStatement(query);
            utils.rs = utils.pst.executeQuery();

            if (utils.rs.next())
                return utils.rs.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public void registerUser(String email, String name, int pin) {
        try {
            utils.pst = utils.conn.prepareStatement("INSERT INTO users(Email, Name, Pin, id) VALUES('" + email + "', '" + name + "', '" + pin + "', '" + usersIdGenerator() + "')");
            utils.pst.executeUpdate();
            user = new User(email, name, pin);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int usersIdGenerator() {
        Random generator = new Random();
        return generator.nextInt(999999999);
    }

    public boolean validateEmailFormat(String strEmail) {
        if (!strEmail.contains("@") && !strEmail.contains("."))
            return false;

        String[] emailSplitter = strEmail.split("@");
        String[] validEmailDomain = {"gmail.com", "hotmail.com", "isep.ipp.pt", "sapo.pt", "outlook.com"};

        for (String s : validEmailDomain) {
            if (Objects.equals(emailSplitter[1], s))
                return true;
        }
        return false;
    }


    public boolean emailExitsOnTheDB(String email) {
        try {
            utils.pst = utils.conn.prepareStatement("SELECT * FROM users WHERE email = '" + email + "'");
            utils.rs = utils.pst.executeQuery();

            if (utils.rs.next())
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public boolean validateEmail(String email) {
        if(emailExitsOnTheDB(email))
            return false;

        return validateEmailFormat(email);
    }



    public boolean validateName(String name) {
        return !name.matches(".*\\d.*");
    }

    public boolean pinMatch(int pin, int pinConfirmation) {
        return pin == pinConfirmation;
    }

    public boolean fourDigitPinCheck(int pin) {
        return pin < 10000 && pin > 999;
    }

    public boolean checkEmpty(String pin, String pinConfirmation) {
        return pinConfirmation.equals("") || pin.equals("");
    }

    public boolean checkIfIsInteger(String pin) {
        return !pin.trim().matches("\\d*");
    }


    public boolean hasFamily(String email) {
        try {
            utils.pst = utils.conn.prepareStatement("SELECT FamilyId FROM users WHERE email = '" + email + "'");
            utils.rs = utils.pst.executeQuery();
            if (utils.rs.next()) {
                return utils.rs.getString(1) != null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public boolean validateLogin(String email, int pin) {
        try {
            utils.pst = utils.conn.prepareStatement("SELECT * FROM users WHERE email = '" + email + "' ");

            utils.rs = utils.pst.executeQuery();

            if (!utils.rs.next())
                return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        try {
            utils.pst = utils.conn.prepareStatement("SELECT pin FROM users WHERE email = '" + email + "'");
            utils.rs = utils.pst.executeQuery();
            if (utils.rs.next()) {
                return utils.rs.getString(1).equals(String.valueOf(pin));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public String getUsersName(String email) {
        try {
            utils.pst = utils.conn.prepareStatement("SELECT name FROM users WHERE email = '" + email + "'");
            utils.rs = utils.pst.executeQuery();

            if (utils.rs.next())
                return utils.rs.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public String getUsersFamilyId(String email) {
        try {
            utils.pst = utils.conn.prepareStatement("SELECT FamilyId FROM users WHERE email = '" + email + "'");
            utils.rs = utils.pst.executeQuery();

            if (utils.rs.next())
                return utils.rs.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public String getUsersFamilyId() {
        try {
            utils.pst = utils.conn.prepareStatement("SELECT FamilyId FROM users WHERE email = '" + user.getEmail() + "'");
            utils.rs = utils.pst.executeQuery();

            if (utils.rs.next())
                return utils.rs.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public void setUser(String email, int pin) {
        user = new User(email, getUsersName(email), pin);
    }


    public String validateInsertedEmail(String email) {
        try {
            utils.pst = utils.conn.prepareStatement("SELECT FamilyId FROM users WHERE email = '" + email + "'");
            utils.rs = utils.pst.executeQuery();

            if (!utils.rs.next())
                return null;
            else
                return utils.rs.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean passwordCheck(String password, String adminEmail) {
        String familyId = getUsersFamilyId(adminEmail);
        try {
            utils.pst = utils.conn.prepareStatement("SELECT Password FROM family WHERE Id = '" + familyId + "'");
            utils.rs = utils.pst.executeQuery();

            if (utils.rs.next())
                return utils.rs.getString(1).equals(password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }


    public void createProduct(String cBoxValue, String txtName, String txtPrice) {
        try {
            utils.pst = utils.conn.prepareStatement("INSERT INTO product(id, name, price, categoryName) VALUES('" + productIdGenerator(cBoxValue) + "', '" + txtName + "', '" + txtPrice + "', '" + cBoxValue + "')");
            utils.pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String productIdGenerator(String category) {
        char[] letters = category.toCharArray();
        Random generator = new Random();

        return letters[0] + "" + generator.nextInt(999999999);
    }

    public List<Product> getProducts() {
        List<Product> familyProduct = new ArrayList<>();
        try {
            utils.pst = utils.conn.prepareStatement("SELECT id, name, price FROM product");
            utils.rs = utils.pst.executeQuery();

            while (utils.rs.next())
                familyProduct.add(new Product(utils.rs.getString(1), utils.rs.getString(2), Double.parseDouble(utils.rs.getString(3))));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return familyProduct;
    }

    public List<Product> filter(String txtName, String cBoxValue) {
        List<Product> filteredList = new ArrayList<>();

        if (cBoxValue != null) {
            try {
                utils.pst = utils.conn.prepareStatement("SELECT id, name, price, categoryName FROM product");
                utils.rs = utils.pst.executeQuery();

                while (utils.rs.next()) {
                    if (utils.rs.getString(2).toLowerCase().contains(txtName.toLowerCase()) && utils.rs.getString(4).equals(cBoxValue))
                        filteredList.add(new Product(utils.rs.getString(1), utils.rs.getString(2), Double.parseDouble(utils.rs.getString(3))));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else {
            try {
                utils.pst = utils.conn.prepareStatement("SELECT id, name, price FROM product");
                utils.rs = utils.pst.executeQuery();

                while (utils.rs.next()) {
                    if (utils.rs.getString(2).toLowerCase().contains(txtName.toLowerCase()))
                        filteredList.add(new Product(utils.rs.getString(1), utils.rs.getString(2), Double.parseDouble(utils.rs.getString(3))));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return filteredList;
    }

    public List<FamilyProduct> getFamilyProductChecked() {
        List<FamilyProduct> checkedProducts = new ArrayList<>();

        String query = "SELECT productId, stock, buy" +
                " FROM " + user.getFamilyId() +
                " WHERE buy = " + 1;

        try {
            utils.pst = utils.conn.prepareStatement(query);
            utils.rs = utils.pst.executeQuery();

            while (utils.rs.next()) {
                checkedProducts.add(new FamilyProduct(utils.rs.getString(1), Integer.parseInt(utils.rs.getString(2)), true));
            }

            return checkedProducts;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getProductsName(String productId) {
        try {
            utils.pst = utils.conn.prepareStatement("SELECT name FROM product WHERE id = '" + productId + "'");
            utils.rs = utils.pst.executeQuery();

            if (utils.rs.next())
                return utils.rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAccount() {
        try {
            utils.pst = utils.conn.prepareStatement("DELETE FROM users WHERE email = '" + user.getEmail() + "'");
            utils.pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProduct(Product product, int stock) {
        try {
            String query = "INSERT INTO " + user.getFamilyId() + "(productId, stock, buy) VALUES(?, ?, ?)";

            utils.pst = utils.conn.prepareStatement(query);
            utils.pst.setString(1, product.getId());
            utils.pst.setString(2, String.valueOf(stock));
            utils.pst.setString(3, String.valueOf(0));

            utils.pst.addBatch();
            utils.pst.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<FamilyProduct> getFamilyProductByCategory(String categoryName) {
        List<FamilyProduct> familyProductsList = new ArrayList<>();


        try {
            ResultSet rsID;
            String query = "SELECT productId FROM " + user.getFamilyId();
            utils.pst = utils.conn.prepareStatement(query);
            rsID = utils.pst.executeQuery();

            while (rsID.next()) {

                utils.pst = utils.conn.prepareStatement("SELECT id FROM product WHERE categoryName = '" + categoryName + "' AND id = '" + rsID.getString(1) + "'");
                utils.rs = utils.pst.executeQuery();

                if (utils.rs.next()) {
                    query = "SELECT productId, stock, buy FROM " + user.getFamilyId() + " WHERE productId = " + "'" + rsID.getString(1) + "'";
                    utils.pst = utils.conn.prepareStatement(query);
                    utils.rs = utils.pst.executeQuery();

                    if (utils.rs.next()) {
                        if (Integer.parseInt(utils.rs.getString(3)) == 0)
                            familyProductsList.add(new FamilyProduct(utils.rs.getString(1), Integer.parseInt(utils.rs.getString(2)), false));
                        else
                            familyProductsList.add(new FamilyProduct(utils.rs.getString(1), Integer.parseInt(utils.rs.getString(2)), true));
                    }
                }

            }

            return familyProductsList;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void checkProduct() throws SQLException {
        utils.pst = utils.conn.prepareStatement("SELECT * FROM product");
        utils.rs = utils.pst.executeQuery();

        if (utils.rs.next())
            System.out.println();
    }

    public void checkBuyOptions(FamilyProduct product, int buy) {
        try {
            String query = "UPDATE " + user.getFamilyId() +
                    " SET buy = " + buy +
                    " WHERE productId = '" + product.getProductId() + "'";
            utils.pst = utils.conn.prepareStatement(query);
            utils.pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getUsersInfo() {
        List<String> list = new ArrayList<>();
        list.add(getUsersName(user.getEmail()));
        list.add(user.getEmail());
        return list;
    }

    public void updateName(String name) {
        try {
            String query = "UPDATE users " +
                    "SET name = '" + name + "'" +
                    " WHERE email = '" + user.getEmail() + "'";
            utils.pst = utils.conn.prepareStatement(query);
            utils.pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmail(String email) {
        try {
            String query = "UPDATE users " +
                    "SET email = '" + email + "'" +
                    " WHERE email = '" + user.getEmail() + "'";
            utils.pst = utils.conn.prepareStatement(query);
            utils.pst.executeUpdate();

            user.setEmail(email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePin(int pin) {
        try {
            String query = "UPDATE users " +
                    "SET pin = '" + pin + "'" +
                    " WHERE email = '" + user.getEmail() + "'";
            utils.pst = utils.conn.prepareStatement(query);
            utils.pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyIfCanBeAdmin(String email) {
        try {
            String query = "SELECT familyId FROM users WHERE email = '" + email + "'";
            utils.pst = utils.conn.prepareStatement(query);
            utils.rs = utils.pst.executeQuery();

            if (!utils.rs.next())
                return false;
            else {
                String familyId = utils.rs.getString(1);
                query = "SELECT adminId FROM family WHERE id = '" + familyId + "'";
                utils.pst = utils.conn.prepareStatement(query);
                utils.rs = utils.pst.executeQuery();

                if(utils.rs.next() && !familyId.equals(utils.rs.getString(1)))
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateFamilysName(String name) {
        try {
            String query = "UPDATE family " +
                    "SET name = '" + name + "'" +
                    " WHERE id = '" + user.getFamilyId() + "'";
            utils.pst = utils.conn.prepareStatement(query);
            utils.pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFamilysEmail(String email) {
        try {
            String query = "UPDATE users " +
                    "SET email = '" + email + "'" +
                    " WHERE id = '" + getFamilyAdminId(family.getId()) + "'";
            utils.pst = utils.conn.prepareStatement(query);
            utils.pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFamilysPassword(String password) {
        try {
            String query = "UPDATE family " +
                    "SET password = '" + password + "'" +
                    " WHERE id = '" + user.getFamilyId() + "'";
            utils.pst = utils.conn.prepareStatement(query);
            utils.pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getFamilysInfo() {
        List<String> famiyInfo = new ArrayList<>();
        famiyInfo.add(getFamilyStringInfo(user.getFamilyId(), "name"));
        famiyInfo.add(getFamilyAdminEmail(getFamilyAdminId(user.getFamilyId())));
        famiyInfo.add(getFamilyStringInfo(user.getFamilyId(), "password"));
        return famiyInfo;
    }

    public void leaveFamily() {
        try {
            String query = "SELECT members FROM family WHERE id = '" + user.getFamilyId() + "'";
            utils.pst = utils.conn.prepareStatement(query);
            utils.rs = utils.pst.executeQuery();

            int members = 0;
            if (utils.rs.next()) {
                members = Integer.parseInt(utils.rs.getString(1));
                members--;
            }

            query = "UPDATE family SET members = " + members + " WHERE id = '" + user.getFamilyId() + "'";
            utils.pst = utils.conn.prepareStatement(query);
            utils.pst.executeUpdate();

            query = "UPDATE users SET familyId = " + null + " WHERE email = '" + user.getEmail() + "'";
            utils.pst = utils.conn.prepareStatement(query);
            utils.pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
