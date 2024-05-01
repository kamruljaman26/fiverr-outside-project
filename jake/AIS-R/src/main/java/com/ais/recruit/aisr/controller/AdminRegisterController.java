package com.ais.recruit.aisr.controller;

import com.ais.recruit.aisr.dao.AdminCsvDao;
import com.ais.recruit.aisr.model.Admin;
import com.ais.recruit.aisr.model.enums.Position;
import com.ais.recruit.aisr.util.FXUtil;
import com.ais.recruit.aisr.util.PasswordUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

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
    }

    public void backButtonAction(ActionEvent actionEvent) {
        FXUtil.loadView(actionEvent, FXUtil.ADMIN_LOGIN_VIEW, "Admin Login");
    }

    public void registerBtnAction(ActionEvent actionEvent) {
        if (validateInput()) {
            try {
                int staffId = Integer.parseInt(staffIdTxtFldId.getText().trim());
                String encryptedPassword = PasswordUtil.encrypt(passwordTxtFldId.getText().trim());
                Admin newAdmin = new Admin(
                        fullnameTxtFldId.getText().trim(),
                        addressTxtFldId.getText().trim(),
                        phoneTxtFldId.getText().trim(),
                        emailTxtFldId.getText().trim(),
                        usernameTxtFldId.getText().trim(),
                        encryptedPassword,
                        staffId,
                        positionComboBoxId.getValue()
                );

                if (adminDao.getById(newAdmin.getEmail()) != null) {
                    errorMsgLabelId.setText("Given email is already registered.");
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
        try {
            Integer.parseInt(staffIdTxtFldId.getText().trim());
        } catch (NumberFormatException e) {
            errorMsgLabelId.setText("Staff ID must be a valid number.");
            return false;
        }
        return true;
    }
}
