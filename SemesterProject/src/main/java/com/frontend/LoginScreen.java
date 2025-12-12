package com.frontend;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.backend.*;


public class LoginScreen {

    private final AuthService authService = new AuthService();

    public void start(Stage stage) {
        Label title = new Label("Facebook");
        title.setStyle("-fx-font-size: 30px; -fx-text-fill: #1877F2; -fx-font-weight: bold;");

        TextField userField = new TextField();
        userField.setPromptText("Email or Phone Number");
        userField.setMaxWidth(250);

        PasswordField passField = new PasswordField();
        passField.setPromptText("Password");
        passField.setMaxWidth(250);

        Button loginButton = new Button("Log In");
        loginButton.setStyle("-fx-background-color: #1877F2; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        loginButton.setMinWidth(250);

        Button signUpBtn = new Button("Sign Up");
        signUpBtn.setStyle("-fx-background-color: #1877F2; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        signUpBtn.setMinWidth(250);

        Label messageLabel = new Label();

        loginButton.setOnAction(event -> {
            String username = userField.getText();
            String password = passField.getText();

            User user = authService.login(username, password);

            if (user != null) {
                new NewsFeed(user).start(stage);
            } else {
                messageLabel.setText("Incorrect password or user not found.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        });

        signUpBtn.setOnAction(e -> {
            new SignUpScreen().start(stage);
        });

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(title, userField, passField, loginButton, messageLabel);

        Scene scene = new Scene(layout, 400, 500);
        stage.setTitle("Login - Facebook");
        stage.setScene(scene);
        stage.show();

    }
}
