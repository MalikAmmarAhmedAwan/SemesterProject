package com.frontend;

import com.backend.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import java.util.*;

public class CommentWindow {

    private Post post;
    private User currentUser;

    public CommentWindow(Post post, User currentUser) {
        this.post = post;
        this.currentUser = currentUser;
    }

    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Comments");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label header = new Label("Comments for " + post.getAuthor() + "'s post:");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        layout.getChildren().addAll(header, new Separator());

        List<Comments> commentsList = post.getComments();
        VBox commentsContainer = new VBox(10);

        if (!commentsList.isEmpty()) {
            for (Comments c : commentsList) {
                VBox commentBox = new VBox(5);
                commentBox.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 10; -fx-background-radius: 5;");

                Label authorLbl = new Label(c.getAuthor().getUsername());
                authorLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #2980b9;");
                Label contentLbl = new Label(c.getContent());
                contentLbl.setWrapText(true);

                commentBox.getChildren().addAll(authorLbl, contentLbl);
                commentsContainer.getChildren().add(commentBox);
            }
        }

        ScrollPane scrollPane = new ScrollPane(commentsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);
        scrollPane.setStyle("-fx-background-color: transparent;");

        TextField commentInput = new TextField();
        commentInput.setPromptText("Write a comment...");
        HBox.setHgrow(commentInput, Priority.ALWAYS);

        Button postBtn = new Button("Post");
        postBtn.setOnAction(e -> {
            if (!commentInput.getText().isEmpty()) {
                String text = commentInput.getText();
                post.addComment(currentUser, text);

                DataStored.getInstance().saveData();

                VBox newBubble = new VBox(5);
                newBubble.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 10; -fx-background-radius: 5;");

                Label authorLbl = new Label(currentUser.getUsername());
                authorLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #2980b9;");

                Label contentLbl = new Label(text);
                contentLbl.setWrapText(true);

                newBubble.getChildren().addAll(authorLbl, contentLbl);
                commentsContainer.getChildren().add(newBubble);

                commentInput.clear();

                scrollPane.setVvalue(1.0);
            }
        });

        HBox inputArea = new HBox(10, commentInput, postBtn);
        inputArea.setAlignment(Pos.CENTER_LEFT);

        Button closeBtn = new Button("Close & Back to Feed");
        closeBtn.setMaxWidth(Double.MAX_VALUE);
        closeBtn.setOnAction(e -> window.close());

        layout.getChildren().addAll(scrollPane, new Separator(), inputArea, new Separator(), closeBtn);

        Scene scene = new Scene(layout, 350, 450);
        window.setScene(scene);
        window.showAndWait();
    }
    private void refreshCommentList(VBox container) {
        container.getChildren().clear();
    }


}