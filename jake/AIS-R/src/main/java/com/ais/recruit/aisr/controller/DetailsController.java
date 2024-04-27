package com.ais.recruit.aisr.controller;

import com.ais.recruit.aisr.util.FXUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {

    public ListView detailsListViewId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void signoutButtonAction(ActionEvent actionEvent) {
        FXUtil.loadView(
                actionEvent,
                FXUtil.START_VIEW,
                "Admin Login"
        );
    }

    public void registrationButtonAction(ActionEvent actionEvent) {
        FXUtil.loadView(
                actionEvent,
                FXUtil.RECRUIT_REGISTRATION_VIEW,
                "Recruit Registration"
        );
    }
}
