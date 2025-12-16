package com.frontend;

import com.backend.AuthService;
import com.backend.DataStored;
import com.backend.User;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AppLauncher extends Application {

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Attempting to load data...");

        DataStored.getInstance().loadData();
        ArrayList<User> loadedUsers = DataStored.getInstance().getUsers();

        AuthService.setRegisteredUsers(loadedUsers);

        new LoginScreen().start(primaryStage);

    }

    public static void main(String[] args) {
        launch(args);
    }
}