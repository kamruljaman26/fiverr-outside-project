package com.ais.recruit.aisr.controller;

import com.ais.recruit.aisr.dao.StaffCsvDao;
import com.ais.recruit.aisr.model.Staff;
import com.ais.recruit.aisr.model.enums.Branch;
import com.ais.recruit.aisr.model.enums.Level;
import com.ais.recruit.aisr.util.FXUtil;
import com.ais.recruit.aisr.util.PasswordUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaffRegisterController implements Initializable {

    public TextField fullnameTxtFldId;
    public TextField addressTxtFldId;
    public TextField phoneTxtFldId;
    public TextField emailTxtFldId;
    public TextField usernameTxtFldId;
    public TextField staffIdTxtFldId;
    public PasswordField passwordTxtFldId;
    public ComboBox<Level> levelComboBoxId;
    public ComboBox<Branch> branchComboBoxId;
    public Label errMsgLabelId;

    private StaffCsvDao staffDao = new StaffCsvDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        levelComboBoxId.getItems().setAll(Level.values());
        branchComboBoxId.getItems().setAll(Branch.values());
        staffDao.load();
    }

    public void backButtonAction(ActionEvent actionEvent) {
        FXUtil.loadView(actionEvent, FXUtil.STAFF_LOGIN_VIEW, "Staff Login");
    }

    public void registerBtnAction(ActionEvent actionEvent) {
        if (validateInput()) {
            try {
                int staffId = Integer.parseInt(staffIdTxtFldId.getText().trim());
                Staff newStaff = new Staff(
                        fullnameTxtFldId.getText().trim(),
                        addressTxtFldId.getText().trim(),
                        phoneTxtFldId.getText().trim(),
                        emailTxtFldId.getText().trim(),
                        usernameTxtFldId.getText().trim(),
                        passwordTxtFldId.getText().trim(),
                        staffId,
                        levelComboBoxId.getValue(),
                        branchComboBoxId.getValue()
                );

                if (!isUniqueStaff(newStaff)) {
                    return; // Exit if not unique
                }

                staffDao.addOrUpdate(newStaff);
                staffDao.save();
                clearFields();
                errMsgLabelId.setText("Staff registered successfully.");
            } catch (InputMismatchException e) {
                errMsgLabelId.setText("Invalid id");
            }
            catch (Exception e) {
                errMsgLabelId.setText("Error in registration: " + e.getMessage());
            }
        }
    }

    private boolean isUniqueStaff(Staff newStaff) {
        List<Staff> staffs = staffDao.getAll();
        for (Staff staff : staffs) {
            if (staff.getEmail().equalsIgnoreCase(newStaff.getEmail())) {
                errMsgLabelId.setText("Email already registered.");
                return false;
            }
            if (staff.getPhone().equals(newStaff.getPhone())) {
                errMsgLabelId.setText("Phone number is already in use.");
                return false;
            }
            if (staff.getUsername().equalsIgnoreCase(newStaff.getUsername())) {
                errMsgLabelId.setText("Username is already taken.");
                return false;
            }
            if (staff.getStaffId() == newStaff.getStaffId()) {
                errMsgLabelId.setText("Staff ID is already assigned.");
                return false;
            }
        }
        return true;
    }

    private boolean validateInput() {
        if (fullnameTxtFldId.getText().isEmpty() || addressTxtFldId.getText().isEmpty() ||
                phoneTxtFldId.getText().isEmpty() || emailTxtFldId.getText().isEmpty() ||
                usernameTxtFldId.getText().isEmpty() || passwordTxtFldId.getText().isEmpty() ||
                staffIdTxtFldId.getText().isEmpty() || levelComboBoxId.getValue() == null ||
                branchComboBoxId.getValue() == null) {
            errMsgLabelId.setText("Please fill in all fields.");
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
        levelComboBoxId.getSelectionModel().clearSelection();
        branchComboBoxId.getSelectionModel().clearSelection();
        errMsgLabelId.setText("");
    }
}
