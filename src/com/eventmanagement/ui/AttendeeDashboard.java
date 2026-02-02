package com.eventmanagement.ui;

import com.eventmanagement.model.Event;
import com.eventmanagement.model.Registration;
import com.eventmanagement.repository.FileEventRepository;
import com.eventmanagement.repository.FileRegistrationRepository;
import com.eventmanagement.service.EventService;
import com.eventmanagement.service.RegistrationService;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AttendeeDashboard {

    private final EventService eventService =
            new EventService(new FileEventRepository());
    private final RegistrationService registrationService =
            new RegistrationService(new FileRegistrationRepository());

    public void show(Stage stage) {

        // ===== Title =====
        Label title = new Label("Attendee Dashboard");
        title.setFont(new Font("Arial", 22));

        // ===== Table =====
        TableView<Event> table = new TableView<>();
        table.setPrefHeight(280);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<Event, String> nameCol = new TableColumn<>("Event Name");
        nameCol.setCellValueFactory(e ->
                new javafx.beans.property.SimpleStringProperty(
                        e.getValue().getName()));

        TableColumn<Event, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(e ->
                new javafx.beans.property.SimpleStringProperty(
                        e.getValue().getDate()));

        TableColumn<Event, String> capCol = new TableColumn<>("Capacity");
        capCol.setCellValueFactory(e ->
                new javafx.beans.property.SimpleStringProperty(
                        String.valueOf(e.getValue().getCapacity())));

        TableColumn<Event, String> adminCol = new TableColumn<>("Created By");
        adminCol.setCellValueFactory(e ->
                new javafx.beans.property.SimpleStringProperty(
                        e.getValue().getCreatedByAdmin()
                )
        );

        table.getColumns().add(adminCol);

        table.getColumns().addAll(nameCol, dateCol, capCol);
        table.setItems(
                FXCollections.observableArrayList(
                        eventService.getAllEvents()
                )
        );

        // ===== Registration Section =====
        Label regTitle = new Label("Register for Selected Event");
        regTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField name = new TextField();
        TextField email = new TextField();
        TextField phone = new TextField();

        name.setPromptText("Full Name");
        email.setPromptText("Email");
        phone.setPromptText("Phone Number");

        name.setMaxWidth(250);
        email.setMaxWidth(250);
        phone.setMaxWidth(250);

        Button registerBtn = new Button("Register");
        registerBtn.setPrefWidth(160);
        registerBtn.setStyle(
                "-fx-background-color: #0d6efd;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;"
        );

        Button logoutBtn = new Button("Logout");
        logoutBtn.setPrefWidth(220);
        logoutBtn.setAlignment(Pos.TOP_RIGHT);
        logoutBtn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: blue;" +
                        "-fx-font-size: 14px;"
        );
        logoutBtn.setUnderline(true); // looks like a link

        registerBtn.setOnAction(e -> {
            try {
                Event selected = table.getSelectionModel().getSelectedItem();
                if (selected == null)
                    throw new IllegalArgumentException("Please select an event");

                if (selected.getCapacity() <= 0)
                    throw new IllegalArgumentException("Event is full");

                registrationService.register(
                        new Registration(
                                "R" + System.currentTimeMillis(),
                                selected.getEventId(),
                                name.getText(),
                                email.getText(),
                                phone.getText()
                        )
                );

                selected.setCapacity(selected.getCapacity() - 1);
                eventService.saveEvent(selected);

                table.refresh();
                showInfo("Registration successful!");

                name.clear();
                email.clear();
                phone.clear();

            } catch (Exception ex) {
                showInfo(ex.getMessage());
            }
        });

        logoutBtn.setOnAction(e -> new Login().show(stage));

        VBox regCard = new VBox(10, regTitle, name, email, phone, registerBtn);
        regCard.setAlignment(Pos.CENTER);
        regCard.setPadding(new Insets(15));
        regCard.setMaxWidth(400);
        regCard.setStyle(
                "-fx-background-color: #f8f9fa;" +
                        "-fx-border-color: #dee2e6;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"
        );

        // ===== Layout =====
        VBox root = new VBox(20, logoutBtn, title, table, new Separator(), regCard);
        root.setPadding(new Insets(25));
        root.setAlignment(Pos.TOP_CENTER);

        stage.setScene(new Scene(root, 700, 550));
        stage.setTitle("Attendee Dashboard");
        stage.show();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
