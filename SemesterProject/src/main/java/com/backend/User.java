package com.backend;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class User {
    protected String id;
    protected String username;
    protected String password;
    protected Profile profile;

    protected List<User> friends;
    protected List<FriendRequest> incomingRequests;
    protected List<FriendRequest> outgoingRequests;
    protected List<Post> posts;

    public User(String username, String password, String id, String fullName, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profile = new Profile(fullName, email);

        this.friends = new ArrayList<>();
        this.incomingRequests = new ArrayList<>();
        this.outgoingRequests = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public void sendFriendRequest(User targetUser) {
        User sender = this;
        FriendRequest newRequest = new FriendRequest(sender, targetUser);
        this.outgoingRequests.add(newRequest);
        targetUser.incomingRequests.add(newRequest);
    }

    public void acceptFriendRequest(FriendRequest request) {
        User sender = request.getFrom();
        User recipient = request.getTo();
        boolean isIntendedForMe = recipient.equals(this);
        boolean isStillPending = request.getStatus() == FriendRequestStatus.PENDING;

        if (isIntendedForMe && isStillPending) {
            request.accept();
            this.friends.add(sender);

            List<User> sendersFriendList = sender.getFriends();
            sendersFriendList.add(this);
        }
    }

    public void rejectFriendRequest(FriendRequest request) {
        User intendedRecipient = request.getTo();
        boolean isForMe = intendedRecipient.equals(this);
        if (isForMe) {
            request.reject();
        }
    }

    public void removeFriend(User targetUser) {
        if (this.friends.contains(targetUser)) {
            this.friends.remove(targetUser);
            targetUser.getFriends().remove(this);
        }
    }

    public Post createPost(String content) {
        Post newPost = new Post(this, content);
        this.posts.add(newPost);
        return newPost;
    }

    public void makeComment(Post postToCommentOn, String commentText) {
        postToCommentOn.addComment(this, commentText);
    }

    public void savePostsToFile() {
        String fileName = this.username + "_posts.txt";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            writer.write("User Feed: " + this.username);
            writer.newLine();
            writer.write("Total Posts: " + posts.size());
            writer.newLine();
            writer.write("========================================");
            writer.newLine();

            for (int i = 0; i < posts.size(); i++) {
                Post p = posts.get(i);

                writer.write("Post ID:  " + (i + 1));
                writer.newLine();

                if (p.date != null) {
                    writer.write("Date:     " + p.date.format(formatter));
                } else {
                    writer.write("Date:     Unknown");
                }
                writer.newLine();

                writer.write("Content:  " + p.getContent());
                writer.newLine();

                writer.write("Likes:    " + p.getLikes());
                writer.newLine();

                writer.write("Comments: " + p.getCommentCount());

                ArrayList<Comments> postComments = p.getComments();
                if(!postComments.isEmpty()) {
                    writer.newLine();
                    writer.write("   --- Comments ---");
                    writer.newLine();
                    for (int j = 0; j < postComments.size(); j++) {
                        Comments c = postComments.get(j);
                        writer.write("   " + (j + 1) + ". " + c.getAuthor() + ": " + c.getContent());
                        writer.newLine();
                    }
                }

                writer.write("----------------------------------------");
                writer.newLine();
            }

            System.out.println("Data successfully saved to " + fileName);

        } catch (IOException e) {
            System.out.println("An error occurred while saving posts: " + e.getMessage());
        }
    }

    public List<FriendRequest> getIncomingRequests() {
        return incomingRequests;
    }

    public List<User> getFriends() {
        return friends;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }

    public List<Post> getPosts() {
        return posts;
    }
}