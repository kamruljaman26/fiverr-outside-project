package com.ais.recruit.aisr.controller;

import com.ais.recruit.aisr.dao.AdminCsvDao;
import com.ais.recruit.aisr.dao.UserDetails;
import com.ais.recruit.aisr.model.Admin;
import com.ais.recruit.aisr.util.FXUtil;
import com.ais.recruit.aisr.util.PasswordUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminLoginController implements Initializable {

    public TextField emailTxtFldId;
    public PasswordField passwordTxtFldId;
    public Label errMsgLabelId;

    private AdminCsvDao adminDao;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adminDao = new AdminCsvDao();
    }

    public void loginBtnAction(ActionEvent actionEvent) {
        String email = emailTxtFldId.getText().trim();
        String password = passwordTxtFldId.getText().trim();

        if (validateInput(email, password)) {
            Admin admin = adminDao.getById(email);
//            System.out.println(admin.getPassword());
//            System.out.println(PasswordUtil.verifyPassword(password, admin.getPassword()));
            if (admin != null && PasswordUtil.verifyPassword(password, admin.getPassword())) {
                errMsgLabelId.setText("Login successful!");

                UserDetails.setCurrentUser(admin); // set logged user

                FXUtil.loadView(actionEvent, FXUtil.DETAILS_VIEW, "AIS-R Dashboard");
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
        FXUtil.loadView(actionEvent, FXUtil.ADMIN_REGISTRATION_VIEW, "Admin Registration");
    }
}
