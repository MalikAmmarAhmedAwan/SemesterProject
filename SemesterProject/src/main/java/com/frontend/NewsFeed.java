package com.frontend;
import com.backend.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class NewsFeed {

    private User currentUser;

    public NewsFeed(User user) {
        this.currentUser = user;
    }

    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        Label welcomeLabel = new Label("Welcome, " + currentUser.getUsername() + "!");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Button logoutBtn = new Button("Log Out");
        logoutBtn.setOnAction(e -> {
            new LoginScreen().start(stage);
        });

        VBox topBar = new VBox(10, welcomeLabel, logoutBtn);
        topBar.setPadding(new Insets(15));
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-width: 0 0 1 0;");
        root.setTop(topBar);

        VBox feedContent = new VBox(15);
        feedContent.setPadding(new Insets(20));

        List<Post> allPosts = new ArrayList<>();

        allPosts.addAll(currentUser.getPosts());

        for (User friend : currentUser.getFriends()) {
            allPosts.addAll(friend.getPosts());
        }

        if (allPosts.isEmpty()) {
            Label emptyLabel = new Label("No posts yet. Add friends to see their updates!");
            feedContent.getChildren().add(emptyLabel);
        } else {
            for (Post p : allPosts) {
                VBox postCard = createPostCard(p);
                feedContent.getChildren().add(postCard);
            }
        }

        ScrollPane scrollPane = new ScrollPane(feedContent);
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 400, 600);
        stage.setScene(scene);
        stage.setTitle("News Feed");
        stage.show();
    }

    private VBox createPostCard(Post p) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-border-color: #dddddd; -fx-border-radius: 5; -fx-background-radius: 5;");

        // 1. Author
        Label authorLabel = new Label(p.getAuthor());
        authorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        authorLabel.setStyle("-fx-text-fill: #2c3e50;");

        // 2. Content
        Label contentLabel = new Label(p.getContent());
        contentLabel.setWrapText(true);
        contentLabel.setFont(Font.font("Arial", 13));

        // 3. Stats
        Label statsLabel = new Label("Likes: " + p.getLikes() + "  •  Comments: " + p.getCommentCount());
        statsLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 11px;");

        // 4. "Show Comments" Button
        Button commentsBtn = new Button("Show Comments");
        // Only enable button if there are actually comments (Optional, but looks nice)
        if (p.getCommentCount() == 0) {
            commentsBtn.setDisable(true);
            commentsBtn.setText("No Comments");
        }

        // ACTION: Open the new window
        commentsBtn.setOnAction(e -> {
            new CommentWindow(p).display();
        });

        card.getChildren().addAll(authorLabel, new Separator(), contentLabel, statsLabel, commentsBtn);

        return card;
    }
}