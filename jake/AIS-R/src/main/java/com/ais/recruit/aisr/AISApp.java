package com.ais.recruit.aisr;

import com.ais.recruit.aisr.util.FXUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AISApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AISApp.class.getResource(FXUtil.START_VIEW));
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        stage.setTitle("AIS-R");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}