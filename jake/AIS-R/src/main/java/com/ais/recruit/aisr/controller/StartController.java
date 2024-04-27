package com.ais.recruit.aisr.controller;

import com.ais.recruit.aisr.util.FXUtil;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class StartController {

    public void administrationBtnAction(ActionEvent actionEvent) {
        // Load the login page
        FXUtil.loadView(
                actionEvent,
                FXUtil.ADMIN_LOGIN_VIEW,
                "Administration Staff Login"
        );
    }

    public void managementBtnAction(ActionEvent actionEvent) {
        // Load the login page
        FXUtil.loadView(
                actionEvent,
                FXUtil.STAFF_LOGIN_VIEW,
                "Management Staff Login"
        );
    }

    public void registerBtnAction(ActionEvent actionEvent) {
//        // Load the login page
//        FXUtil.loadView(
//                actionEvent,
//                FXUtil.TY,
//                "AIS-R"
//        );
    }
}
