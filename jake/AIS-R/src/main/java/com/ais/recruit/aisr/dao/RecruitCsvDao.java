package com.ais.recruit.aisr.dao;

import com.ais.recruit.aisr.model.Recruit;
import com.ais.recruit.aisr.model.enums.*;
import com.ais.recruit.aisr.util.PasswordUtil;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RecruitCsvDao implements DAO<Recruit> {

    private List<Recruit> recruits = new ArrayList<>();
    public static final String FILENAME = "database/recruits.csv";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public Recruit getById(String email) {
        for (Recruit recruit : recruits) {
            if (recruit.getEmail().equals(email)) {
                return recruit;
            }
        }
        return null;
    }

    @Override
    public List<Recruit> getAll() {
        return new ArrayList<>(recruits);
    }

    @Override
    public void addOrUpdate(Recruit recruit) {
        int index = getIdFor(recruit);
        if (index != -1) {
            // Check if password has been updated
            if (!recruit.getPassword().equals(recruits.get(index).getPassword())) {
                recruit.setPassword(PasswordUtil.encrypt(recruit.getPassword()));
            }
            recruits.set(index, recruit);
        } else {
            // Encrypt password before adding new recruit
            recruit.setPassword(PasswordUtil.encrypt(recruit.getPassword()));
            recruits.add(recruit);
        }
    }


    @Override
    public int getIdFor(Recruit recruit) {
        for (int i = 0; i < recruits.size(); i++) {
            if (recruits.get(i).getEmail().equals(recruit.getEmail())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void remove(Recruit recruit) {
        recruits.remove(recruit);
    }

    @Override
    public boolean save() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILENAME))) {
            for (Recruit recruit : recruits) {
                out.println(recruitToCsvLine(recruit));
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error saving recruits to CSV: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            recruits.clear();
            while ((line = reader.readLine()) != null) {
                Recruit recruit = csvLineToRecruit(line);
                if (recruit != null) {
                    recruits.add(recruit);
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error loading recruits from CSV: " + e.getMessage());
            return false;
        }
    }

    private String recruitToCsvLine(Recruit recruit) {
        return recruit.getFullName() + "," + recruit.getAddress() + "," + recruit.getPhone() + "," +
                recruit.getEmail() + "," + recruit.getDegree() + "," + recruit.getQualification() + "," +
                recruit.getHigherQualification() + "," +
                recruit.getDateOfInterview().format(formatter) + "," +
                recruit.getLevel() + "," + recruit.getBranch();
    }

    private Recruit csvLineToRecruit(String line) {
        try {
            String[] data = line.split(",");
            if (data.length != 10) {
                throw new IllegalArgumentException("Invalid number of fields in CSV line.");
            }
            String fullName = data[0];
            String address = data[1];
            String phone = data[2];
            String email = data[3];
            String degree = data[4];
            String qualification = data[5];
            String higherQualification = data[6];
            LocalDateTime dateOfInterview = LocalDateTime.parse(data[7], formatter);
            Level level = Level.valueOf(data[8]);
            Branch branch = Branch.valueOf(data[9]);
            Recruit recruit = new Recruit();
            recruit.setFullName(fullName);
            recruit.setAddress(address);
            recruit.setPhone(phone);
            recruit.setEmail(email);
            recruit.setDegree(degree);
            recruit.setQualification(qualification);
            recruit.setHigherQualification(higherQualification);
            recruit.setDateOfInterview(dateOfInterview);
            recruit.setLevel(level);
            recruit.setBranch(branch);
            return recruit;
        } catch (Exception e) {
            System.err.println("Error parsing CSV line: " + e.getMessage());
            return null;
        }
    }
}
