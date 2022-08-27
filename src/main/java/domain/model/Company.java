package domain.model;

import domain.shared.Utils;

import java.sql.SQLException;
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
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
            utils.pst = utils.conn.prepareStatement("SELECT AdminId FROM family WHERE Id = '" + familyId + "'");
            utils.rs = utils.pst.executeQuery();

            if (utils.rs.next())
                return Integer.parseInt(utils.rs.getString(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
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

        }
        else {
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
                if (Integer.parseInt(utils.rs.getString(3)) == 0)
                    checkedProducts.add(new FamilyProduct(utils.rs.getString(1), Integer.parseInt(utils.rs.getString(2)), false));
                else
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

            if(utils.rs.next())
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
            String query = "SELECT productId FROM " + user.getFamilyId();
            utils.pst = utils.conn.prepareStatement(query);
            utils.rs = utils.pst.executeQuery();

            while (utils.rs.next()) {

                try {
                    utils.pst = utils.conn.prepareStatement("SELECT id FROM product WHERE categoryName = '" + categoryName + "' AND id = '" + utils.rs.getString(1) + "'");
                    utils.rs = utils.pst.executeQuery();

                    if(utils.rs.next())
                        try {
                            query = "SELECT productId, stock, buy FROM " + user.getFamilyId() + " WHERE productId = " + "'" + utils.rs.getString(1) + "'";
                            utils.pst = utils.conn.prepareStatement(query);
                            utils.rs = utils.pst.executeQuery();

                            if(utils.rs.next()) {
                                if (Integer.parseInt(utils.rs.getString(3)) == 0)
                                    familyProductsList.add(new FamilyProduct(utils.rs.getString(1), Integer.parseInt(utils.rs.getString(2)), false));
                            } else
                                familyProductsList.add(new FamilyProduct(utils.rs.getString(1), Integer.parseInt(utils.rs.getString(2)), true));

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                } catch (SQLException e) {
                    e.printStackTrace();
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
}
