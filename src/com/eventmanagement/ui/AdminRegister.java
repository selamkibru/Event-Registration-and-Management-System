package com.eventmanagement.ui;

import com.eventmanagement.repository.FileAdminRepository;
import com.eventmanagement.service.AdminAuthService;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminRegister {

    private final AdminAuthService auth =
            new AdminAuthService(new FileAdminRepository());

    public void show(Stage stage) {

        TextField fullName = new TextField();
        TextField username = new TextField();
        TextField email = new TextField();
        TextField phone = new TextField();
        PasswordField password = new PasswordField();
        PasswordField confirm = new PasswordField();

        fullName.setPromptText("Full Name");
        username.setPromptText("Username");
        email.setPromptText("Email");
        phone.setPromptText("Phone");
        password.setPromptText("Password");
        confirm.setPromptText("Confirm Password");

        Button create = new Button("Create Account");

        create.setOnAction(e -> {
            if (!password.getText().equals(confirm.getText())) {
                alert("Passwords do not match");
                return;
            }

            try {
                auth.register(
                        fullName.getText(),
                        username.getText(),
                        email.getText(),
                        phone.getText(),
                        password.getText()
                );
                alert("Account created successfully!");
                new AdminLogin().show(stage);
            } catch (Exception ex) {
                alert(ex.getMessage());
            }
        });

        VBox root = new VBox(10,
                fullName, username, email, phone,
                password, confirm, create);

        stage.setScene(new Scene(root, 350, 400));
        stage.setTitle("Create Admin Account");
        stage.show();
    }

    private void alert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(msg);
        a.showAndWait();
    }
}
