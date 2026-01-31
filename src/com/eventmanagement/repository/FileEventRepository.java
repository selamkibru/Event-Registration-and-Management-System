package com.eventmanagement.repository;

import com.eventmanagement.model.Event;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileEventRepository implements EventRepository {

    private static final String FILE_PATH = "events.csv";
    
    public void save(Event event) {
        List<Event> events = findAll();
        boolean updated = false;

        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getEventId().equals(event.getEventId())) {
                events.set(i, event); // update existing
                updated = true;
                break;
            }
        }

        if (!updated) {
            events.add(event); // add new
        }

        writeAll(events);
    }

    public List<Event> findAll() {
        List<Event> events = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Event e = new Event(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
                events.add(e);
            }
        } catch (FileNotFoundException e) {
            // File not found is OK for first run
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }

    public Event findById(String id) {
        for (Event e : findAll()) {
            if (e.getEventId().equals(id)) return e;
        }
        return null;
    }

    public void delete(String id) {
        List<Event> events = findAll();
        events.removeIf(e -> e.getEventId().equals(id));
        writeAll(events);
    }

    private void writeAll(List<Event> events) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Event e : events) {
                bw.write(e.getEventId() + "," + e.getName() + "," + e.getDate() + "," + e.getCapacity());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
