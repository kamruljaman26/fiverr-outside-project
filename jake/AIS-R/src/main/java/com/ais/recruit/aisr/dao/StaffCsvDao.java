package com.ais.recruit.aisr.dao;

import com.ais.recruit.aisr.model.Staff;
import com.ais.recruit.aisr.model.enums.Branch;
import com.ais.recruit.aisr.model.enums.Level;
import com.ais.recruit.aisr.util.PasswordUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StaffCsvDao implements DAO<Staff> {

    private List<Staff> staffMembers = new ArrayList<>();
    public static final String FILENAME = "database/staff.csv";

    public StaffCsvDao() {
        load();
    }

    @Override
    public Staff getById(String email) {
        for (Staff staff : staffMembers) {
            if (staff.getEmail().equals(email)) {
                return staff;
            }
        }
        return null;
    }

    @Override
    public List<Staff> getAll() {
        System.out.println(staffMembers);
        return new ArrayList<>(staffMembers);
    }

    @Override
    public void addOrUpdate(Staff staff) {
        int index = getIdFor(staff);
        if (index != -1) {
            // Check if password has been updated
            if (!staff.getPassword().equals(staffMembers.get(index).getPassword())) {
                staff.setPassword(PasswordUtil.encrypt(staff.getPassword()));
            }
            staffMembers.set(index, staff);
        } else {
            // Encrypt password before adding new staff
            staff.setPassword(PasswordUtil.encrypt(staff.getPassword()));
            staffMembers.add(staff);
        }
    }


    @Override
    public int getIdFor(Staff staff) {
        for (int i = 0; i < staffMembers.size(); i++) {
            if (staffMembers.get(i).getEmail().equals(staff.getEmail())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void remove(Staff staff) {
        staffMembers.remove(staff);
    }

    @Override
    public boolean save() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILENAME))) {
            for (Staff staff : staffMembers) {
                out.println(staffToCsvLine(staff));
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error saving staff to CSV: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            staffMembers.clear();
            while ((line = reader.readLine()) != null) {
                Staff staff = csvLineToStaff(line);
                if (staff != null) {
                    staffMembers.add(staff);
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error loading staff from CSV: " + e.getMessage());
            return false;
        }
    }

    private String staffToCsvLine(Staff staff) {
        return staff.getStaffId() + "," + staff.getFullName() + "," + staff.getAddress() + "," +
                staff.getPhone() + "," + staff.getEmail() + "," + staff.getUsername() + "," +
                staff.getPassword() + "," + staff.getLevel() + "," + staff.getBranch();
    }

    private Staff csvLineToStaff(String line) {
        try {
            String[] data = line.split(",");
            if (data.length != 9) {
                throw new IllegalArgumentException("Invalid number of fields in CSV line.");
            }
            int staffId = Integer.parseInt(data[0]);
            String fullName = data[1];
            String address = data[2];
            String phone = data[3];
            String email = data[4];
            String username = data[5];
            String password = data[6];
            Level level = Level.valueOf(data[7]);
            Branch branch = Branch.valueOf(data[8]);
            Staff staff = new Staff();
            staff.setFullName(fullName);
            staff.setAddress(address);
            staff.setPhone(phone);
            staff.setEmail(email);
            staff.setUsername(username);
            staff.setPassword(password);
            staff.setStaffId(staffId);
            staff.setLevel(level);
            staff.setBranch(branch);
            return staff;
        } catch (Exception e) {
            System.err.println("Error parsing CSV line: " + e.getMessage());
            return null;
        }
    }
}
