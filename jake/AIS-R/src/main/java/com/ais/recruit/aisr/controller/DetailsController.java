package com.ais.recruit.aisr.controller;

import com.ais.recruit.aisr.dao.RecruitCsvDao;
import com.ais.recruit.aisr.dao.UserDetails;
import com.ais.recruit.aisr.model.Recruit;
import com.ais.recruit.aisr.util.FXUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DetailsController implements Initializable {

    public TextField searchTxtFldId;
    public TableView<Recruit> detailsTableViewId;
    private ObservableList<Recruit> recruitData;
    private final RecruitCsvDao recruitDao = new RecruitCsvDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recruitDao.load();
        recruitData = FXCollections.observableArrayList(recruitDao.getAll());
        setupTableView();
    }

    private void setupTableView() {
        TableColumn<Recruit, String> fullNameCol = new TableColumn<>("Full Name");
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        TableColumn<Recruit, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Recruit, String> degreeCol = new TableColumn<>("Degree");
        degreeCol.setCellValueFactory(new PropertyValueFactory<>("degree"));

        TableColumn<Recruit, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Recruit, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Recruit, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Recruit, Void> call(final TableColumn<Recruit, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("View Details");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Recruit data = getTableView().getItems().get(getIndex());
                            System.out.println("Selected Recruit: " + data.getFullName());
                            FXUtil.loadView(event, FXUtil.RECRUIT_DETAILS_VIEW, "Recruit Details for " + data.getFullName(), data);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox container = new HBox();
                            container.getChildren().add(btn);
                            setGraphic(container);
                        }
                    }
                };
            }
        });

        detailsTableViewId.getColumns().addAll(fullNameCol, addressCol, degreeCol, emailCol, usernameCol, actionCol);
        detailsTableViewId.setItems(recruitData);
    }

    public void signoutButtonAction(ActionEvent actionEvent) {
        FXUtil.loadView(actionEvent, FXUtil.START_VIEW, "Admin Login");
        UserDetails.setCurrentUser(null); // Clearing user session
    }

    public void registrationButtonAction(ActionEvent actionEvent) {
        FXUtil.loadView(actionEvent, FXUtil.RECRUIT_REGISTRATION_VIEW, "Recruit Registration");
    }

    public void searchBtnAction(ActionEvent actionEvent) {
        String searchText = searchTxtFldId.getText().toLowerCase();
        List<Recruit> filtered = recruitDao.getAll().stream()
                .filter(r -> r.getFullName().toLowerCase().contains(searchText) ||
                        r.getAddress().toLowerCase().contains(searchText) ||
                        r.getEmail().toLowerCase().contains(searchText) ||
                        r.getUsername().toLowerCase().contains(searchText))
                .collect(Collectors.toList());
        recruitData.setAll(filtered);
    }
}
