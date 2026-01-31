package com.eventmanagement.ui;

import com.eventmanagement.model.Event;
import com.eventmanagement.repository.FileEventRepository;
import com.eventmanagement.service.EventService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminDashboard {

    private EventService eventService = new EventService(new FileEventRepository());

    public void show(Stage stage) {
        VBox root = new VBox(10);

        TextField nameField = new TextField();
        nameField.setPromptText("Event Name");

        TextField dateField = new TextField();
        dateField.setPromptText("Event Date");

        TextField capacityField = new TextField();
        capacityField.setPromptText("Capacity");

        Button addBtn = new Button("Add Event");

        // Table to display events
        TableView<Event> table = new TableView<>();
        TableColumn<Event, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        TableColumn<Event, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDate()));

        TableColumn<Event, String> capCol = new TableColumn<>("Capacity");
        capCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getCapacity())));

        table.getColumns().addAll(nameCol, dateCol, capCol);

        // Load events
        refreshTable(table);

        addBtn.setOnAction(e -> {
            try {
                String name = nameField.getText();
                String date = dateField.getText();
                int capacity = Integer.parseInt(capacityField.getText());

                eventService.saveEvent(new Event("E" + System.currentTimeMillis(), name, date, capacity));
                refreshTable(table);

                nameField.clear();
                dateField.clear();
                capacityField.clear();
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        root.getChildren().addAll(new Label("Add Event"), nameField, dateField, capacityField, addBtn, table);

        Scene scene = new Scene(root, 500, 400);
        stage.setTitle("Admin Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    private void refreshTable(TableView<Event> table) {
        ObservableList<Event> events = FXCollections.observableArrayList(eventService.getAllEvents());
        table.setItems(events);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

