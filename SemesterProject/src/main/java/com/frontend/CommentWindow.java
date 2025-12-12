package com.frontend;

import com.backend.Comments;
import com.backend.Post;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class CommentWindow {

    private Post post;

    public CommentWindow(Post post) {
        this.post = post;
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
        VBox commentsContainer = new VBox(10); // Container for the actual comments

        if (commentsList.isEmpty()) {
            commentsContainer.getChildren().add(new Label("No comments yet."));
        } else {
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
        scrollPane.setPrefHeight(300); // Limit height so the button stays visible
        scrollPane.setStyle("-fx-background-color: transparent;");

        Button closeBtn = new Button("Close & Back to Feed");
        closeBtn.setMaxWidth(Double.MAX_VALUE); // Make button full width
        closeBtn.setOnAction(e -> window.close());

        layout.getChildren().addAll(scrollPane, new Separator(), closeBtn);

        Scene scene = new Scene(layout, 350, 450);
        window.setScene(scene);
        window.showAndWait();
    }
}