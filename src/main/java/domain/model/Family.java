package domain.model;

import java.util.Random;

public class Family {

    String id;
    String name;
    int members;
    int adminId;
    String password;

    public Family(int adminId) {
        this.adminId = adminId;
    }


    public Family(String name, int adminId) {
        this.name = name;
        this.id = idGenerator();
        this.adminId = adminId;
    }

    public String idGenerator() {
        Random generator = new Random();
        StringBuilder id = new StringBuilder();

        char[] letters = name.toCharArray();

        id.append(letters[0]).append(letters[1]).append(letters[2]).append(generator.nextInt(9999999));

        return id.toString();
    }

    public String getId() {
        return id;
    }
}
