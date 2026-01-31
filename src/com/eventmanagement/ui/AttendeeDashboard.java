package com.eventmanagement.ui;

import com.eventmanagement.model.Event;
import com.eventmanagement.model.Registration;
import com.eventmanagement.repository.FileRegistrationRepository;
import com.eventmanagement.service.RegistrationService;
import com.eventmanagement.repository.FileEventRepository;
import com.eventmanagement.service.EventService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AttendeeDashboard {

    private EventService eventService = new EventService(new FileEventRepository());
    private RegistrationService registrationService = new RegistrationService(new FileRegistrationRepository());

    public void show(Stage stage) {
        VBox root = new VBox(10);

        // Table to show events
        TableView<Event> table = new TableView<>();
        TableColumn<Event, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        TableColumn<Event, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDate()));
        TableColumn<Event, String> capCol = new TableColumn<>("Capacity");
        capCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getCapacity())));

        table.getColumns().addAll(nameCol, dateCol, capCol);
        refreshTable(table);

        TextField attendeeIdField = new TextField();
        attendeeIdField.setPromptText("Your Attendee ID");

        Button registerBtn = new Button("Register for Selected Event");

        registerBtn.setOnAction(e -> {
            try {
                Event selected = table.getSelectionModel().getSelectedItem();
                if (selected == null) throw new IllegalArgumentException("Select an event first");
                String attendeeId = attendeeIdField.getText();
                registrationService.register(new Registration("R" + System.currentTimeMillis(), selected.getEventId(), attendeeId));
                showAlert("Success", "Registered successfully!");
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        root.getChildren().addAll(new Label("Available Events"), table, attendeeIdField, registerBtn);

        Scene scene = new Scene(root, 500, 400);
        stage.setTitle("Attendee Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    private void refreshTable(TableView<Event> table) {
        ObservableList<Event> events = FXCollections.observableArrayList(eventService.getAllEvents());
        table.setItems(events);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

