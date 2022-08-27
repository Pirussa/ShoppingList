package domain.shared;

public class MenuItem {

    String title;
    Runnable ui;

    public MenuItem(String title, Runnable ui) {
        this.title = title;
        this.ui = ui;
    }

    public void run() {
        this.ui.run();
    }

    public String toString()
    {
        return this.title;
    }
}

