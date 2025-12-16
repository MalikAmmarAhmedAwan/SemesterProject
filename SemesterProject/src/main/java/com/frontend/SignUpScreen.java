package com.frontend;

import com.backend.AuthService;
import com.backend.DataStored;
import com.backend.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignUpScreen {

    public void start(Stage primaryStage) {
        Label titleLabel = new Label("Create Account");
        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Full Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField userField = new TextField();
        userField.setPromptText("Username");

        PasswordField passField = new PasswordField();
        passField.setPromptText("Password");

        Button registerBtn = new Button("Register");
        Button backBtn = new Button("Back to Login");

        Label messageLabel = new Label();

        registerBtn.setOnAction(e -> {
            String name = fullNameField.getText();
            String email = emailField.getText();
            String user = userField.getText();
            String pass = passField.getText();

            if (name.isEmpty() || email.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                messageLabel.setText("Please fill all fields.");
                return;
            }

            AuthService auth = new AuthService();
            User newUser = auth.register(user, pass, name, email);
            DataStored.getInstance().addUser(newUser);

            if (newUser != null) {
                messageLabel.setText("Success! Please Go Back and Login.");
                fullNameField.clear();
                emailField.clear();
                userField.clear();
                passField.clear();
            } else {
                messageLabel.setText("Username already taken.");
            }
        });

        backBtn.setOnAction(e -> {
            new LoginScreen().start(primaryStage);
        });

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(titleLabel, fullNameField, emailField, userField, passField, registerBtn, backBtn, messageLabel);

        Scene scene = new Scene(layout, 300, 350);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sign Up");
        primaryStage.show();
    }
}