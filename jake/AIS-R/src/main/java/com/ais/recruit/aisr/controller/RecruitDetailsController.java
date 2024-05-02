package com.ais.recruit.aisr.controller;

import com.ais.recruit.aisr.model.enums.Level;
import com.ais.recruit.aisr.util.FXUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RecruitDetailsController implements Initializable {

    public TextField fullnameTxtFldId;
    public PasswordField addressTxtFldId;
    public TextField phoneTxtFldId;
    public PasswordField emailTxtFldId;
    public TextField degreeTxtFldId;
    public PasswordField qualificationTxtFldId;
    public TextField staffIDTxtFldId;
    public ComboBox<Level> levelComboBoxId;
    public ComboBox<Level> interviewComboBoxId;
    public ComboBox<Level> branchComboBoxId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    public void backButtonAction(ActionEvent actionEvent) {
        FXUtil.loadView(
                actionEvent,
                FXUtil.DETAILS_VIEW,
                "Recruit Details"
        );
    }

    public void submitBtnAction(ActionEvent actionEvent) {
        System.out.println("Submitted");
    }
}
