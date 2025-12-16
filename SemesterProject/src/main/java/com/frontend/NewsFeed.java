package com.frontend;

import com.backend.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class NewsFeed {

    private User currentUser;
    private List<User> allSystemUsers;
    private VBox feedContent;

    public NewsFeed(User user, List<User> allUsers) {
        this.currentUser = user;
        this.allSystemUsers = allUsers;
    }

    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        Label welcomeLabel = new Label("Welcome, " + currentUser.getUsername() + "!");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Button profileBtn = new Button("My Profile");
        profileBtn.setStyle("-fx-background-color: #1877F2; -fx-text-fill: white; -fx-font-weight: bold;");
        profileBtn.setOnAction(e -> new UserProfileScreen(currentUser).start(stage));

        long pendingCount = currentUser.getIncomingRequests().stream()
                .filter(r -> r.getStatus() == FriendRequestStatus.PENDING).count();

        Button requestsBtn = new Button("Requests (" + pendingCount + ")");
        requestsBtn.setStyle("-fx-background-color: #e4e6eb; -fx-text-fill: black; -fx-font-weight: bold;");
        requestsBtn.setOnAction(e -> showRequestsPopup(requestsBtn));

        Button logoutBtn = new Button("Log Out");
        logoutBtn.setOnAction(e -> new LoginScreen().start(stage));

        Button addFriendBtn = new Button("Find People +");
        addFriendBtn.setStyle("-fx-background-color: #42b72a; -fx-text-fill: white; -fx-font-weight: bold;");
        addFriendBtn.setOnAction(e -> showAddFriendPopup());

        HBox rightButtons = new HBox(10, profileBtn, requestsBtn, logoutBtn, addFriendBtn);
        rightButtons.setAlignment(Pos.CENTER_RIGHT);

        BorderPane topBar = new BorderPane();
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-width: 0 0 1 0;");
        topBar.setLeft(welcomeLabel);
        topBar.setRight(rightButtons);
        root.setTop(topBar);

        feedContent = new VBox(15);
        feedContent.setPadding(new Insets(20));

        refreshFeed();

        ScrollPane scrollPane = new ScrollPane(feedContent);
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 700, 600);
        stage.setScene(scene);
        stage.setTitle("News Feed");
        stage.show();
    }

    private void refreshFeed() {
        feedContent.getChildren().clear();

        List<Post> allPosts = new ArrayList<>(currentUser.getPosts());

        for (User friend : currentUser.getFriends()) {
            allPosts.addAll(friend.getPosts());
        }

        if (allPosts.isEmpty()) {
            Label emptyLabel = new Label("No posts yet. Click 'Find People' to connect with others!");
            emptyLabel.setFont(Font.font("Arial", 14));
            feedContent.getChildren().add(emptyLabel);
        } else {
            for (Post p : allPosts) {
                feedContent.getChildren().add(createPostCard(p));
            }
        }
    }

    private void showRequestsPopup(Button originBtn) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Friend Requests");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label header = new Label("Pending Requests");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        layout.getChildren().add(header);

        List<FriendRequest> myRequests = currentUser.getIncomingRequests();
        boolean hasPending = false;

        for (FriendRequest req : myRequests) {
            if (req.getStatus() == FriendRequestStatus.PENDING) {
                hasPending = true;

                HBox row = new HBox(10);
                row.setAlignment(Pos.CENTER_LEFT);
                row.setPadding(new Insets(10));
                row.setStyle("-fx-border-color: #ddd; -fx-border-width: 0 0 1 0;");

                Label nameLabel = new Label(req.getFrom().getUsername() + " sent you a request.");
                nameLabel.setFont(Font.font("Arial", 13));

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                Button acceptBtn = new Button("Confirm");
                acceptBtn.setStyle("-fx-background-color: #1877F2; -fx-text-fill: white;");

                Button rejectBtn = new Button("Delete");
                rejectBtn.setStyle("-fx-background-color: #e4e6eb; -fx-text-fill: black;");

                acceptBtn.setOnAction(e -> {
                    currentUser.acceptFriendRequest(req);

                    layout.getChildren().remove(row);
                    refreshFeed();

                    updateRequestButtonCount(originBtn);
                });

                rejectBtn.setOnAction(e -> {
                    currentUser.rejectFriendRequest(req);
                    layout.getChildren().remove(row);
                    updateRequestButtonCount(originBtn);
                });

                row.getChildren().addAll(nameLabel, spacer, acceptBtn, rejectBtn);
                layout.getChildren().add(row);
            }
        }

        if (!hasPending) {
            layout.getChildren().add(new Label("No pending requests."));
        }

        Scene scene = new Scene(layout, 400, 400);
        popup.setScene(scene);
        popup.show();
    }

    private void updateRequestButtonCount(Button btn) {
        long count = currentUser.getIncomingRequests().stream()
                .filter(r -> r.getStatus() == FriendRequestStatus.PENDING).count();
        btn.setText("Requests (" + count + ")");
    }

    private void showAddFriendPopup() {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Find Friends");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label header = new Label("People You May Know");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        layout.getChildren().add(header);

        boolean foundStranger = false;

        if (allSystemUsers != null) {
            for (User u : allSystemUsers) {
                if (u.getUsername().equals(currentUser.getUsername())) continue;

                boolean isAlreadyFriend = false;
                for(User friend : currentUser.getFriends()) {
                    if(friend.getUsername().equals(u.getUsername())) {
                        isAlreadyFriend = true;
                        break;
                    }
                }

                if (!isAlreadyFriend) {
                    foundStranger = true;
                    HBox row = new HBox(10);
                    row.setAlignment(Pos.CENTER_LEFT);
                    row.setPadding(new Insets(5));
                    row.setStyle("-fx-border-color: #eee; -fx-border-width: 0 0 1 0;");

                    Label name = new Label(u.getUsername());
                    name.setStyle("-fx-font-weight: bold;");

                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);

                    Button addBtn = new Button("Add Friend");

                    addBtn.setOnAction(e -> {
                        currentUser.sendFriendRequest(u);
                        addBtn.setText("Request Sent");
                        addBtn.setDisable(true);
                        addBtn.setStyle("-fx-background-color: gray; -fx-text-fill: white;");
                    });

                    row.getChildren().addAll(name, spacer, addBtn);
                    layout.getChildren().add(row);
                }
            }
        }

        if (!foundStranger) {
            layout.getChildren().add(new Label("No new people to show!"));
        }

        Scene scene = new Scene(layout, 300, 400);
        popup.setScene(scene);
        popup.show();
    }

    private VBox createPostCard(Post p) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-border-color: #dddddd; -fx-border-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);");

        Label author = new Label(p.getAuthor());
        author.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        author.setTextFill(Color.DARKBLUE);

        Label content = new Label(p.getContent());
        content.setWrapText(true);
        content.setFont(Font.font("Arial", 13));

        HBox actionBar = new HBox(15);
        actionBar.setAlignment(Pos.CENTER_LEFT);
        actionBar.setPadding(new Insets(10, 0, 0, 0));

        Button likeBtn = new Button();
        updateLikeButtonUI(likeBtn, p);

        likeBtn.setOnAction(e -> {
            if (p.hasLiked(currentUser)) {
                p.unlike(currentUser);
            } else {
                p.like(currentUser);
            }
            updateLikeButtonUI(likeBtn, p);
        });

        Button commentBtn = new Button("💬 Comments (" + p.getCommentCount() + ")");
        commentBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #666; -fx-border-color: #ddd; -fx-border-radius: 3;");

        commentBtn.setOnAction(e -> {
            CommentWindow win = new CommentWindow(p, currentUser);
            win.display();
            commentBtn.setText("💬 Comments (" + p.getCommentCount() + ")");
        });

        actionBar.getChildren().addAll(likeBtn, commentBtn);
        card.getChildren().addAll(author, content, new Separator(), actionBar);
        return card;
    }

    private void updateLikeButtonUI(Button btn, Post p) {
        int count = p.getLikes();
        if (p.hasLiked(currentUser)) {
            btn.setText("♥ " + count);
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #e0245e; -fx-font-weight: bold; -fx-border-color: #e0245e; -fx-border-radius: 3;");
        } else {
            btn.setText("♥ " + count);
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #666; -fx-border-color: #ddd; -fx-border-radius: 3;");
        }
    }
}