package com.ais.recruit.aisr.controller;

import com.ais.recruit.aisr.dao.AdminCsvDao;
import com.ais.recruit.aisr.model.Admin;
import com.ais.recruit.aisr.model.enums.Position;
import com.ais.recruit.aisr.util.FXUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminRegisterController implements Initializable {

    public TextField fullnameTxtFldId;
    public TextField addressTxtFldId;
    public TextField phoneTxtFldId;
    public TextField emailTxtFldId;
    public TextField usernameTxtFldId;
    public TextField staffIdTxtFldId;
    public PasswordField passwordTxtFldId;
    public ComboBox<Position> positionComboBoxId;
    public Label errorMsgLabelId;

    private AdminCsvDao adminDao = new AdminCsvDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        positionComboBoxId.getItems().setAll(Position.values());
        adminDao.load();
    }

    public void backButtonAction(ActionEvent actionEvent) {
        FXUtil.loadView(actionEvent, FXUtil.ADMIN_LOGIN_VIEW, "Admin Login");
    }

    public void registerBtnAction(ActionEvent actionEvent) {
        if (validateInput()) {
            try {
                int staffId = Integer.parseInt(staffIdTxtFldId.getText().trim());
                Admin newAdmin = new Admin(
                        fullnameTxtFldId.getText().trim(),
                        addressTxtFldId.getText().trim(),
                        phoneTxtFldId.getText().trim(),
                        emailTxtFldId.getText().trim(),
                        usernameTxtFldId.getText().trim(),
                        passwordTxtFldId.getText().trim(),
                        staffId,
                        positionComboBoxId.getValue()
                );

                if (!isUniqueAdmin(newAdmin)) {
                    return;
                }

                adminDao.addOrUpdate(newAdmin);
                adminDao.save();
                clearFields();
                errorMsgLabelId.setText("Admin registered successfully.");
            } catch (Exception e) {
                errorMsgLabelId.setText("Error in registration: " + e.getMessage());
            }
        }
    }

    private boolean isUniqueAdmin(Admin newAdmin) {
        List<Admin> admins = adminDao.getAll();
        for (Admin admin : admins) {
            if (admin.getEmail().equalsIgnoreCase(newAdmin.getEmail())) {
                errorMsgLabelId.setText("Given email is already registered.");
                return false;
            }
            if (admin.getPhone().equals(newAdmin.getPhone())) {
                errorMsgLabelId.setText("Phone number is already in use.");
                return false;
            }
            if (admin.getUsername().equalsIgnoreCase(newAdmin.getUsername())) {
                errorMsgLabelId.setText("Username is already taken.");
                return false;
            }
            if (admin.getStaffId() == newAdmin.getStaffId()) {
                errorMsgLabelId.setText("Staff ID is already assigned.");
                return false;
            }
        }
        return true;
    }

    private boolean validateInput() {
        if (fullnameTxtFldId.getText().isEmpty() ||
                addressTxtFldId.getText().isEmpty() ||
                phoneTxtFldId.getText().isEmpty() ||
                emailTxtFldId.getText().isEmpty() ||
                usernameTxtFldId.getText().isEmpty() ||
                passwordTxtFldId.getText().isEmpty() ||
                staffIdTxtFldId.getText().isEmpty() ||
                positionComboBoxId.getValue() == null) {
            errorMsgLabelId.setText("All fields must be filled and valid.");
            return false;
        }

        if (!validatePhoneNumber(phoneTxtFldId.getText())) {
            errorMsgLabelId.setText("Invalid phone format. Expected format: XXX-XXX-XXXX");
            return false;
        }

        if (!validateEmail(emailTxtFldId.getText())) {
            errorMsgLabelId.setText("Invalid email format.");
            return false;
        }

        try {
            Integer.parseInt(staffIdTxtFldId.getText().trim());
        } catch (NumberFormatException e) {
            errorMsgLabelId.setText("Staff ID must be a valid number.");
            return false;
        }

        return true;
    }

    private boolean validatePhoneNumber(String phone) {
        Pattern pattern = Pattern.compile("^\\d{3}-\\d{3}-\\d{4}$");
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
        staffIdTxtFldId.clear();
        passwordTxtFldId.clear();
        positionComboBoxId.getSelectionModel().clearSelection();
    }
}
