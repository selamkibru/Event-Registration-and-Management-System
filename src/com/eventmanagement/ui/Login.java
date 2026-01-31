package com.eventmanagement.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login {

    public void show(Stage stage) {
        VBox root = new VBox(10);

        Button adminBtn = new Button("Login as Admin");
        Button attendeeBtn = new Button("Login as Attendee");

        adminBtn.setOnAction(e -> new AdminDashboard().show(stage));
        attendeeBtn.setOnAction(e -> new AttendeeDashboard().show(stage));

        root.getChildren().addAll(adminBtn, attendeeBtn);

        Scene scene = new Scene(root, 300, 150);
        stage.setTitle("Event Registration System - Login");
        stage.setScene(scene);
        stage.show();
    }
}

