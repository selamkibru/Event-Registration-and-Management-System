package com.eventmanagement.ui;

import com.eventmanagement.model.Event;
import com.eventmanagement.repository.FileEventRepository;
import com.eventmanagement.service.EventService;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.List;

public class AdminDashboard {

    private final EventService eventService =
            new EventService(new FileEventRepository());

    public void show(Stage stage) {

        // ===== Title =====
        Label title = new Label("Admin Dashboard");
        title.setFont(new Font("Arial", 22));

        // ===== Form Title =====
        Label formTitle = new Label("Create New Event");
        formTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // ===== Form =====
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(12);

        TextField nameField = new TextField();
        TextField dateField = new TextField();
        TextField capacityField = new TextField();

        nameField.setPromptText("Event Name");
        dateField.setPromptText("YYYY-MM-DD");
        capacityField.setPromptText("Capacity");

        Button addBtn = new Button("Add Event");
        addBtn.setPrefWidth(150);
        addBtn.setStyle(
                "-fx-background-color: #198754;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;"
        );

        Button deleteBtn = new Button("Delete Selected Event");
        deleteBtn.setStyle(
                "-fx-background-color: #dc3545; " +
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

        form.add(new Label("Event Name:"), 0, 0);
        form.add(nameField, 1, 0);
        form.add(new Label("Date:"), 0, 1);
        form.add(dateField, 1, 1);
        form.add(new Label("Capacity:"), 0, 2);
        form.add(capacityField, 1, 2);
        form.add(addBtn, 1, 3);

        // ===== Form Card =====
        VBox formCard = new VBox(10, formTitle, form);
        formCard.setPadding(new Insets(15));
        formCard.setMaxWidth(450);
        formCard.setStyle(
                "-fx-background-color: #f8f9fa;" +
                        "-fx-border-color: #dee2e6;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"
        );

        // ===== Table =====
        TableView<Event> table = new TableView<>();
        table.setPrefHeight(250);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<Event, String> nameCol = new TableColumn<>("Name");
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

        table.getColumns().addAll(nameCol, dateCol, capCol);
        refreshTable(table);

        // ===== Button Action =====
        addBtn.setOnAction(e -> {
            try {
                eventService.saveEvent(
                        new Event(
                                "E" + System.currentTimeMillis(),
                                nameField.getText(),
                                dateField.getText(),
                                Integer.parseInt(capacityField.getText()),
                                com.eventmanagement.util.Session
                                        .getLoggedInAdmin()
                                        .getFullName()
                        )
                );
                refreshTable(table);
                nameField.clear();
                dateField.clear();
                capacityField.clear();
            } catch (Exception ex) {
                showAlert(ex.getMessage());
            }
        });

        deleteBtn.setOnAction(e -> {
            Event selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String adminName = com.eventmanagement.util.Session.getLoggedInAdmin().getFullName();

                if (!selected.getCreatedByAdmin().equals(adminName)) {
                    showAlert("You can only delete events you created!");
                    return;
                }

                eventService.deleteEvent(selected.getEventId());
                refreshTable(table);
            }
        });

        logoutBtn.setOnAction(e -> {
            // Clear session
            com.eventmanagement.util.Session.setLoggedInAdmin(null);
            // Go back to login screen
            new Login().show(stage);
        });

        // ===== Layout =====
        VBox root = new VBox(20, logoutBtn, title, formCard, new Separator(), table, deleteBtn);
        root.setPadding(new Insets(25));
        root.setAlignment(Pos.TOP_CENTER);

        stage.setScene(new Scene(root, 700, 550));
        stage.setTitle("Admin Dashboard");
        stage.show();
    }

    private void refreshTable(TableView<Event> table) {
        String adminName = com.eventmanagement.util.Session.getLoggedInAdmin().getFullName();

        // Filter events to only those created by this admin
        List<Event> adminEvents = eventService.getAllEvents().stream()
                .filter(e -> e.getCreatedByAdmin().equals(adminName))
                .toList();

        table.setItems(FXCollections.observableArrayList(adminEvents));
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
