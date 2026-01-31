package com.eventmanagement.ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage stage) {
        Login login = new Login();
        login.show(stage); // Start with login screen
    }

    public static void main(String[] args) {
        launch();
    }
}

