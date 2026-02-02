package com.eventmanagement.repository;

import com.eventmanagement.model.AdminAccount;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileAdminRepository implements AdminRepository {

    private static final String FILE_PATH = "admins.csv";

    public void save(AdminAccount admin) {
        List<AdminAccount> admins = findAll();
        admins.add(admin);
        writeAll(admins);
    }

    public List<AdminAccount> findAll() {
        List<AdminAccount> admins = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length < 6) continue;
                admins.add(new AdminAccount(
                        p[0],  // adminId
                        p[1],  // fullName
                        p[2],  // username
                        p[3],  // email
                        p[4],  // phone
                        p[5]   // password
                ));
            }
        } catch (IOException ignored) {}

        return admins;
    }

    public AdminAccount findById(String id) {
        return findAll().stream()
                .filter(a -> a.getAdminId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public AdminAccount findByUsername(String username) {
        return findAll().stream()
                .filter(a -> a.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void delete(String id) {}

    private void writeAll(List<AdminAccount> admins) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (AdminAccount a : admins) {
                bw.write(
                        a.getAdminId() + "," +
                                a.getFullName() + "," +
                                a.getUsername() + "," +
                                a.getEmail() + "," +
                                a.getPhone() + "," +
                                a.getPassword()
                );
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
