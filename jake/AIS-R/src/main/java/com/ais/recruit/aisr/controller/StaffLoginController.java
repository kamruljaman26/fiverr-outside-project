package com.ais.recruit.aisr.controller;

import com.ais.recruit.aisr.dao.StaffCsvDao;
import com.ais.recruit.aisr.dao.UserDetails;
import com.ais.recruit.aisr.model.Staff;
import com.ais.recruit.aisr.util.FXUtil;
import com.ais.recruit.aisr.util.PasswordUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class StaffLoginController implements Initializable {

    public TextField emailTxtFldId;
    public PasswordField passwordTxtFldId;
    public Label errMsgLabelId;

    private StaffCsvDao staffDao = new StaffCsvDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void loginBtnAction(ActionEvent actionEvent) {
        String email = emailTxtFldId.getText().trim();
        String password = passwordTxtFldId.getText().trim();

        if (validateInput(email, password)) {
            Staff staff = staffDao.getById(email);
            System.out.println(staff);
            if (staff != null && PasswordUtil.verifyPassword(password, staff.getPassword())) {
                errMsgLabelId.setText("Login successful!");

                UserDetails.setCurrentUser(staff);

                FXUtil.loadView(actionEvent, FXUtil.DETAILS_VIEW, "Staff Dashboard");
            } else {
                errMsgLabelId.setText("Invalid email or password.");
            }
        } else {
            errMsgLabelId.setText("Please enter both email and password.");
        }
    }

    private boolean validateInput(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }

    public void backButtonAction(ActionEvent actionEvent) {
        FXUtil.loadView(actionEvent, FXUtil.START_VIEW, "AIS-R Start");
    }

    public void registerButtonAction(ActionEvent actionEvent) {
        FXUtil.loadView(actionEvent, FXUtil.STAFF_REGISTRATION_VIEW, "Staff Registration");
    }
}
