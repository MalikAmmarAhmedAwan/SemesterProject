package com.frontend;


import javafx.application.Application;
import javafx.stage.Stage;

public class AppLauncher extends Application {
    @Override
    public void start(Stage primaryStage) {
        new LoginScreen().start(primaryStage);
    }

    public static void main(String[] args) {
        com.backend.DataLoader.populateData();

        launch(args);
    }
}
