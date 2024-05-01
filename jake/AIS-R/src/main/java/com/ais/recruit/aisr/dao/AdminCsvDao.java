package com.ais.recruit.aisr.dao;

import com.ais.recruit.aisr.model.Admin;
import com.ais.recruit.aisr.model.enums.Position;
import com.ais.recruit.aisr.util.PasswordUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdminCsvDao implements DAO<Admin> {

    private List<Admin> admins = new ArrayList<>();
    public static final String FILENAME = "database/admins.csv";

    @Override
    public Admin getById(String email) {
        for (Admin admin : admins) {
            if (admin.getEmail().equals(email)) {
                return admin;
            }
        }
        return null;
    }

    @Override
    public List<Admin> getAll() {
        return new ArrayList<>(admins);
    }

    @Override
    public void addOrUpdate(Admin admin) {
        int index = getIdFor(admin);
        if (index != -1) {
            // if password updated, re-encrypt
            if(!admin.getPassword().equals(admins.get(index).getPassword())){
                admin.setPassword(PasswordUtil.encrypt(admin.getPassword()));
            }
            admins.set(index, admin);
        } else {
            // Add Password encryption
            admin.setPassword(PasswordUtil.encrypt(admin.getPassword()));
            admins.add(admin);
        }
    }

    @Override
    public int getIdFor(Admin admin) {
        for (int i = 0; i < admins.size(); i++) {
            if (admins.get(i).getEmail().equals(admin.getEmail())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void remove(Admin admin) {
        admins.remove(admin);
    }

    @Override
    public boolean save() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILENAME))) {
            for (Admin admin : admins) {
                out.println(adminToCsvLine(admin));
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error saving admins to CSV: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            admins.clear();
            while ((line = reader.readLine()) != null) {
                Admin admin = csvLineToAdmin(line);
                if (admin != null) {
                    admins.add(admin);
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error loading admins from CSV: " + e.getMessage());
            return false;
        }
    }

    private String adminToCsvLine(Admin admin) {
        return admin.getStaffId() + "," + admin.getFullName() + "," + admin.getAddress() + "," +
                admin.getPhone() + "," + admin.getEmail() + "," + admin.getUsername() + "," +
                admin.getPassword() + "," + admin.getPosition().toString();
    }

    private Admin csvLineToAdmin(String line) {
        try {
            String[] data = line.split(",");
            if (data.length != 8) {
                throw new IllegalArgumentException("Invalid number of fields in CSV line.");
            }
            int staffId = Integer.parseInt(data[0]);
            String fullName = data[1];
            String address = data[2];
            String phone = data[3];
            String email = data[4];
            String username = data[5];
            String password = data[6];
            Position position = Position.valueOf(data[7]);
            return new Admin(fullName, address, phone, email, username, password, staffId, position);
        } catch (Exception e) {
            System.err.println("Error parsing CSV line: " + e.getMessage());
            return null;
        }
    }
}
