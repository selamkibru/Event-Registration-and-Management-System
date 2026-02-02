package com.eventmanagement.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Login {

    public void show(Stage stage) {

        // Title
        Label title = new Label("Event Registration System");
        title.setFont(new Font("Arial", 20));

        // Buttons
        Button adminBtn = new Button("Login as Admin");
        Button attendeeBtn = new Button("Login as Attendee");

        adminBtn.setPrefWidth(200);
        attendeeBtn.setPrefWidth(200);

        adminBtn.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-background-color: #2c7be5;" +
                        "-fx-text-fill: white;"
        );

        attendeeBtn.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-background-color: #6c757d;" +
                        "-fx-text-fill: white;"
        );

        // Actions
        adminBtn.setOnAction(e -> new AdminLogin().show(stage));
        attendeeBtn.setOnAction(e -> new AttendeeDashboard().show(stage));

        // Layout
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(title, adminBtn, attendeeBtn);

        Scene scene = new Scene(root, 400, 250);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
}

