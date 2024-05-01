package com.ais.recruit.aisr.controller;

import com.ais.recruit.aisr.util.FXUtil;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    public void loginBtnAction(ActionEvent actionEvent) {
        FXUtil.loadView(
                actionEvent,
                FXUtil.DETAILS_VIEW,
                "AIS-R"
        );
    }

    public void backButtonAction(ActionEvent actionEvent) {
        FXUtil.loadView(
                actionEvent,
                FXUtil.START_VIEW,
                "AIS-R"
        );
    }

    public void registerButtonAction(ActionEvent actionEvent) {
        FXUtil.loadView(
                actionEvent,
                FXUtil.ADMIN_REGISTRATION_VIEW,
                "Admin Registration"
        );
    }
}
