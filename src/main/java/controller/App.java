package controller;

import domain.model.Company;

public class App {

    private Company company;


    private App() {
        this.company = new Company();
    }

    public Company getCompany() {
        return this.company;
    }

    private static App singleton = null;

    public static App getInstance() {
        if (singleton == null) {
            synchronized (App.class) {
                singleton = new App();
            }
        }
        return singleton;
    }
}
