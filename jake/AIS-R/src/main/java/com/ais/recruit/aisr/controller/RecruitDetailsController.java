package com.ais.recruit.aisr.controller;

import com.ais.recruit.aisr.dao.RecruitCsvDao;
import com.ais.recruit.aisr.model.Recruit;
import com.ais.recruit.aisr.model.enums.Branch;
import com.ais.recruit.aisr.model.enums.Level;
import com.ais.recruit.aisr.util.DataTraveler;
import com.ais.recruit.aisr.util.FXUtil;
import com.ais.recruit.aisr.util.PasswordUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class RecruitDetailsController implements Initializable, DataTraveler {

    public TextField fullnameTxtFldId;
    public TextField addressTxtFldId;
    public TextField phoneTxtFldId;
    public TextField emailTxtFldId;
    public PasswordField passwordTxtFldId;
    public TextField qualificationsTxtFldId;
    public TextField degreeTxtFldId;
    public TextField staffIDTxtFldId;
    public ComboBox<LocalDateTime> interviewComboBoxId;
    public ComboBox<Level> levelComboBoxId;
    public ComboBox<Branch> branchComboBoxId;
    public Label errMsgLabelId;

    private Recruit recruit;
    private RecruitCsvDao recruitDao = new RecruitCsvDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        levelComboBoxId.getItems().setAll(Level.values());
        branchComboBoxId.getItems().setAll(Branch.values());
        setupInterviewDateComboBox();
        recruitDao.load();
    }

    private void setupInterviewDateComboBox() {
        interviewComboBoxId.getItems().addAll(
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusWeeks(1)
        );
    }

    @Override
    public void data(Object... data) {
        this.recruit = (Recruit) data[0];
        loadDataToForm();
    }

    private void loadDataToForm() {
        if (recruit != null) {
            fullnameTxtFldId.setText(recruit.getFullName());
            addressTxtFldId.setText(recruit.getAddress());
            phoneTxtFldId.setText(recruit.getPhone());
            emailTxtFldId.setText(recruit.getEmail());
            qualificationsTxtFldId.setText(recruit.getQualification());
            degreeTxtFldId.setText(recruit.getDegree());
            staffIDTxtFldId.setText(String.valueOf(recruit.getStaffId()));
            interviewComboBoxId.setValue(recruit.getDateOfInterview());
            levelComboBoxId.setValue(recruit.getLevel());
            branchComboBoxId.setValue(recruit.getBranch());
        }
    }

    public void backButtonAction(ActionEvent actionEvent) {
        FXUtil.loadView(actionEvent, FXUtil.DETAILS_VIEW, "Admin Login");
    }

    public void submitBtnAction(ActionEvent actionEvent) {
        if (validateInput()) {
            updateRecruitDetails();
            recruitDao.addOrUpdate(recruit);
            recruitDao.save();
            FXUtil.loadView(actionEvent, FXUtil.DETAILS_VIEW, "Recruit Details Updated Successfully");
        }
    }

    private boolean validateInput() {
        if (fullnameTxtFldId.getText().isEmpty() || addressTxtFldId.getText().isEmpty() ||
                phoneTxtFldId.getText().isEmpty() || emailTxtFldId.getText().isEmpty() ||
                qualificationsTxtFldId.getText().isEmpty() || degreeTxtFldId.getText().isEmpty()) {
            errMsgLabelId.setText("Please fill in all fields and select an interview date.");
            return false;
        }
        return true;
    }

    private void updateRecruitDetails() {
        recruit.setFullName(fullnameTxtFldId.getText());
        recruit.setAddress(addressTxtFldId.getText());
        recruit.setPhone(phoneTxtFldId.getText());
        recruit.setEmail(emailTxtFldId.getText());
        recruit.setQualification(qualificationsTxtFldId.getText());
        recruit.setDegree(degreeTxtFldId.getText());
        recruit.setDateOfInterview(interviewComboBoxId.getValue());
        recruit.setLevel(levelComboBoxId.getValue());
        recruit.setBranch(branchComboBoxId.getValue());
    }
}
