package com.eventmanagement.ui;

import com.eventmanagement.repository.FileAdminRepository;
import com.eventmanagement.service.AdminAuthService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import com.eventmanagement.model.AdminAccount;

public class AdminLogin {

    private final AdminAuthService authService =
            new AdminAuthService(new FileAdminRepository());

    public void show(Stage stage) {

        // ===== Title =====
        Label title = new Label("Admin Login");
        title.setFont(new Font("Arial", 20));

        // ===== Inputs =====
        TextField username = new TextField();
        username.setPromptText("Username");
        username.setMaxWidth(220);

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setMaxWidth(220);

        // ===== Buttons =====
        Button loginBtn = new Button("Login");
        loginBtn.setPrefWidth(220);
        loginBtn.setStyle(
                "-fx-background-color: #0d6efd;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;"
        );

        Button registerBtn = new Button("Create New Account");
        registerBtn.setPrefWidth(220);
        registerBtn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #373636;" +
                        "-fx-font-size: 13px;"
        );

        Button forgotBtn = new Button("Forgot Password");
        forgotBtn.setPrefWidth(220);
        forgotBtn.setAlignment(Pos.BOTTOM_RIGHT);
        forgotBtn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: blue;" +
                        "-fx-font-size: 12px;"
        );
        forgotBtn.setUnderline(true); // looks like a link

        Button backBtn = new Button("Go Back");
        backBtn.setPrefWidth(220);
        backBtn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: blue;" +
                        "-fx-font-size: 11px;" +
                        "-fx-alignment: left;"
        );

        // ===== Actions =====
        loginBtn.setOnAction(e -> {
            try {
                AdminAccount admin = authService.login(
                        username.getText(),
                        password.getText()
                );

                 // store admin in session
                com.eventmanagement.util.Session.setLoggedInAdmin(admin);

                new AdminDashboard().show(stage);

            } catch (Exception ex) {
                showAlert(ex.getMessage());
            }
        });

        registerBtn.setOnAction(e ->
                new AdminRegister().show(stage)
        );

        forgotBtn.setOnAction(e ->
                showAlert("Please contact the system administrator to reset your password")
        );

        backBtn.setOnAction(e -> new Login().show(stage));

        // ===== Card Layout =====

        VBox card = new VBox(12, title, username, password, loginBtn, forgotBtn, registerBtn, backBtn);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: #f8f9fa;" +
                        "-fx-border-color: #dee2e6;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;"
        );

        VBox root = new VBox(card);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        stage.setScene(new Scene(root, 350, 300));
        stage.setTitle("Admin Login");
        stage.show();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
