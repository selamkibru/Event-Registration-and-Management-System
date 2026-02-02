package com.eventmanagement.repository;

import com.eventmanagement.model.Registration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileRegistrationRepository implements RegistrationRepository {

    private static final String FILE_PATH = "registrations.csv";

    @Override
    public void save(Registration reg) {
        List<Registration> regs = findAll();
        boolean updated = false;

        for (int i = 0; i < regs.size(); i++) {
            if (regs.get(i).getRegistrationId().equals(reg.getRegistrationId())) {
                regs.set(i, reg);
                updated = true;
                break;
            }
        }

        if (!updated) {
            regs.add(reg);
        }

        writeAll(regs);
    }

    @Override
    public List<Registration> findAll() {
        List<Registration> regs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) continue; // Skip bad lines
                Registration r = new Registration(
                        parts[0],
                        parts[1],
                        parts[2],
                        parts[3],
                        parts[4]
                );
                regs.add(r);
            }
        } catch (FileNotFoundException e) {
            // File not found is OK
        } catch (IOException e) {
            e.printStackTrace();
        }
        return regs;
    }

    @Override
    public Registration findById(String id) {
        for (Registration r : findAll()) {
            if (r.getRegistrationId().equals(id)) return r;
        }
        return null;
    }

    @Override
    public void delete(String id) {
        List<Registration> regs = findAll();
        regs.removeIf(r -> r.getRegistrationId().equals(id));
        writeAll(regs);
    }

    private void writeAll(List<Registration> regs) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Registration r : regs) {
                bw.write(
                        r.getRegistrationId() + "," +
                                r.getEventId() + "," +
                                r.getAttendeeName() + "," +
                                r.getEmail() + "," +
                                r.getPhone()
                );
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

