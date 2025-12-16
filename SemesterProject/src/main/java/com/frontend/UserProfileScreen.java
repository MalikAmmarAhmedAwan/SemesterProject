package com.frontend;

import com.backend.User;
import com.backend.Profile;
import com.backend.Post;
import com.backend.AuthService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class UserProfileScreen {

    private final User user;

    public UserProfileScreen(User user) {
        this.user = user;
    }

    public void start(Stage stage) {
        if (user.getProfile() == null) {
        }
        Profile profile = user.getProfile();

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("My Profile");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1877F2;");

        Label usernameLabel = new Label("Username: @" + user.getUsername());
        usernameLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: grey;");

        Label emailLabel = new Label("Email: " + (user.getEmail() == null ? "No email set" : user.getEmail()));
        Button editEmailBtn = new Button("✎");
        setupEditButton(editEmailBtn, "Edit Email", "Enter new email:", newValue -> {
            user.setEmail(newValue); 
            emailLabel.setText("Email: " + newValue);
        });
        HBox emailBox = new HBox(10, emailLabel, editEmailBtn);
        emailBox.setAlignment(Pos.CENTER);

        Label cityLabel = new Label("City: " + profile.getCity());
        Button editCityBtn = new Button("✎");
        setupEditButton(editCityBtn, "Edit City", "Enter your city:", newValue -> {
            profile.setCity(newValue);
            cityLabel.setText("City: " + newValue);
        });
        HBox cityBox = new HBox(10, cityLabel, editCityBtn);
        cityBox.setAlignment(Pos.CENTER);

        Label bioLabel = new Label("Bio: " + profile.getBio());
        bioLabel.setWrapText(true);
        Button editBioBtn = new Button("✎");
        setupEditButton(editBioBtn, "Edit Bio", "Enter a short bio:", newValue -> {
            profile.setBio(newValue);
            bioLabel.setText("Bio: " + newValue);
        });
        HBox bioBox = new HBox(10, bioLabel, editBioBtn);
        bioBox.setAlignment(Pos.CENTER);

        Label postsCountLabel = new Label("Total Posts: " + user.getPosts().size());

        Separator separator = new Separator();

        Label newPostHeader = new Label("Create New Post");
        newPostHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        TextArea postInput = new TextArea();
        postInput.setPromptText("What's on your mind?");
        postInput.setPrefHeight(80);
        postInput.setMaxWidth(300);
        postInput.setWrapText(true);

        Button postBtn = new Button("Post Status");
        postBtn.setStyle("-fx-background-color: #42b72a; -fx-text-fill: white; -fx-font-weight: bold;");

        Label statusMsg = new Label();

        postBtn.setOnAction(e -> {
            String content = postInput.getText().trim();
            if (!content.isEmpty()) {
                Post newPost = new Post(user.getUsername(), content);

                user.getPosts().add(newPost);

                postInput.clear();
                postsCountLabel.setText("Total Posts: " + user.getPosts().size());
                statusMsg.setText("Post published!");
                statusMsg.setStyle("-fx-text-fill: green;");
            } else {
                statusMsg.setText("Post cannot be empty.");
                statusMsg.setStyle("-fx-text-fill: red;");
            }
        });

        Button backBtn = new Button("← Back to News Feed");
        backBtn.setOnAction(e -> {
            new NewsFeed(user, AuthService.getRegisteredUsers()).start(stage);
        });

        layout.getChildren().addAll(
                title,
                usernameLabel,
                emailBox,
                cityBox,
                bioBox,
                postsCountLabel,
                separator,
                newPostHeader,
                postInput,
                postBtn,
                statusMsg,
                new Separator(),
                backBtn
        );

        Scene scene = new Scene(layout, 400, 600);
        stage.setTitle("My Profile");
        stage.setScene(scene);
        stage.show();
    }

    private void setupEditButton(Button btn, String title, String header, EditAction action) {
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #1877F2; -fx-font-weight: bold; -fx-border-color: #ddd;");
        btn.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle(title);
            dialog.setHeaderText(header);
            dialog.setContentText("Value:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(newValue -> {
                if (!newValue.trim().isEmpty()) {
                    action.execute(newValue.trim());
                }
            });
        });
    }

    interface EditAction {
        void execute(String newValue);
    }
}