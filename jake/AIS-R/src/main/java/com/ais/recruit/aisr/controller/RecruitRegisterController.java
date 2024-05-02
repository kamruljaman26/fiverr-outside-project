package com.ais.recruit.aisr.controller;

import com.ais.recruit.aisr.dao.RecruitCsvDao;
import com.ais.recruit.aisr.model.Recruit;
import com.ais.recruit.aisr.model.enums.Branch;
import com.ais.recruit.aisr.model.enums.Level;
import com.ais.recruit.aisr.util.FXUtil;
import com.ais.recruit.aisr.util.PasswordUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecruitRegisterController implements Initializable {

    public TextField fullnameTxtFldId;
    public TextField addressTxtFldId;
    public TextField phoneTxtFldId;
    public TextField emailTxtFldId;
    public TextField usernameTxtFldId;
    public PasswordField passwordTxtFldId;
    public TextField qualificationsTxtFldId;
    public TextField degreeTxtFldId;
    public ComboBox<LocalDateTime> interviewDateComboBoxId;
    public Label errMsgLabelId;

    private RecruitCsvDao recruitDao = new RecruitCsvDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupInterviewDateComboBox();
    }

    private void setupInterviewDateComboBox() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

        // Populate the ComboBox with example dates
        interviewDateComboBoxId.getItems().addAll(
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(3),
                LocalDateTime.now().plusDays(4),
                LocalDateTime.now().plusDays(5),
                LocalDateTime.now().plusDays(6),
                LocalDateTime.now().plusDays(7)
        );

        // Set the cell factory to use the formatter
        interviewDateComboBoxId.setCellFactory(comboBox -> new ListCell<LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(dateFormatter.format(item));
                }
            }
        });

        // Ensure the selected item is also formatted correctly
        interviewDateComboBoxId.setButtonCell(new ListCell<LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(dateFormatter.format(item));
                }
            }
        });
    }

    public void backButtonAction(ActionEvent actionEvent) {
        FXUtil.loadView(actionEvent, FXUtil.DETAILS_VIEW, "Admin Login");
    }

    public void registerBtnAction(ActionEvent actionEvent) {
        if (validateInput()) {
            try {
                Recruit newRecruit = new Recruit(
                        fullnameTxtFldId.getText().trim(),
                        addressTxtFldId.getText().trim(),
                        phoneTxtFldId.getText().trim(),
                        emailTxtFldId.getText().trim(),
                        usernameTxtFldId.getText().trim(),
                        passwordTxtFldId.getText().trim(),
                        degreeTxtFldId.getText().trim(),
                        qualificationsTxtFldId.getText().trim(),
                        "",
                        interviewDateComboBoxId.getValue(),
                        Level.NONE,
                        Branch.NONE
                );

                if (!isUnique(newRecruit)) {
                    return;
                }

                recruitDao.addOrUpdate(newRecruit);
                recruitDao.save();
                clearFields();
                errMsgLabelId.setText("Recruit registered successfully.");
            } catch (Exception e) {
                e.printStackTrace();
                errMsgLabelId.setText("Error in registration: " + e.getMessage());
            }
        }
    }

    private boolean isUnique(Recruit recruit) {
        List<Recruit> allRecruits = recruitDao.getAll();
        for (Recruit existingRecruit : allRecruits) {
            if (existingRecruit.getEmail().equalsIgnoreCase(recruit.getEmail())) {
                errMsgLabelId.setText("Email is already in use.");
                return false;
            }
            if (existingRecruit.getPhone().equals(recruit.getPhone())) {
                errMsgLabelId.setText("Phone number is already in use.");
                return false;
            }
            if (existingRecruit.getUsername().equalsIgnoreCase(recruit.getUsername())) {
                errMsgLabelId.setText("Username is already taken.");
                return false;
            }
        }
        return true;
    }

    private boolean validateInput() {
        if (fullnameTxtFldId.getText().isEmpty() || addressTxtFldId.getText().isEmpty() ||
                phoneTxtFldId.getText().isEmpty() || emailTxtFldId.getText().isEmpty() ||
                usernameTxtFldId.getText().isEmpty() || passwordTxtFldId.getText().isEmpty() ||
                qualificationsTxtFldId.getText().isEmpty() || degreeTxtFldId.getText().isEmpty() ||
                interviewDateComboBoxId.getValue() == null) {
            errMsgLabelId.setText("Please fill in all fields and select an interview date.");
            return false;
        }

        if (!validatePhoneNumber(phoneTxtFldId.getText())) {
            errMsgLabelId.setText("Invalid phone format. Expected format: XXX-XXX-XXXX");
            return false;
        }

        if (!validateEmail(emailTxtFldId.getText())) {
            errMsgLabelId.setText("Invalid email format.");
            return false;
        }

        return true;
    }

    private boolean validatePhoneNumber(String phone) {
        Pattern pattern = Pattern.compile("^\\d{3}-\\d{3}-\\d{4}$");  // Adjust pattern as necessary
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    private boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void clearFields() {
        fullnameTxtFldId.clear();
        addressTxtFldId.clear();
        phoneTxtFldId.clear();
        emailTxtFldId.clear();
        usernameTxtFldId.clear();
        passwordTxtFldId.clear();
        qualificationsTxtFldId.clear();
        degreeTxtFldId.clear();
        interviewDateComboBoxId.getSelectionModel().clearSelection();
    }
}
