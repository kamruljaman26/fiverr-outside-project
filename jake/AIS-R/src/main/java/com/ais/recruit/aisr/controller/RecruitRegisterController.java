package com.ais.recruit.aisr.controller;

import com.ais.recruit.aisr.util.FXUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RecruitRegisterController implements Initializable {


    public TextField fullnameTxtFldId;
    public PasswordField addressTxtFldId;
    public TextField phoneTxtFldId;
    public PasswordField emailTxtFldId;
    public TextField usernameTxtFldId;
    public PasswordField passwordTxtFldId;
    public ComboBox positionComboBoxId;
    public TextField qualificationsTxtFldId;
    public PasswordField degreeTxtFldId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    public void backButtonAction(ActionEvent actionEvent) {
        FXUtil.loadView(
                actionEvent,
                FXUtil.DETAILS_VIEW,
                "Admin Login"
        );
    }

    public void registerBtnAction(ActionEvent actionEvent) {
        System.out.println("success");
    }
}
