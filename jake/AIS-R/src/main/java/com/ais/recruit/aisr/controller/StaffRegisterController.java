package com.ais.recruit.aisr.controller;

import com.ais.recruit.aisr.util.FXUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class StaffRegisterController implements Initializable {

    public TextField fullnameTxtFldId;
    public PasswordField addressTxtFldId;
    public TextField phoneTxtFldId;
    public PasswordField emailTxtFldId;
    public TextField usernameTxtFldId;
    public TextField staffIdTxtFldId;
    public PasswordField passwordTxtFldId;
    public ComboBox levelComboBoxId;
    public ComboBox branchComboBoxId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    public void backButtonAction(ActionEvent actionEvent) {
        FXUtil.loadView(
                actionEvent,
                FXUtil.STAFF_LOGIN_VIEW,
                "Staff Login"
        );
    }

    public void registerBtnAction(ActionEvent actionEvent) {
        System.out.println("Information submitted");
    }
}
