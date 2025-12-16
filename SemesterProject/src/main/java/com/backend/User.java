package com.backend;
import java.io.Serializable;
import java.util.*;


public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String id;
    protected String username;
    protected String password;
    protected Profile profile;
    protected String email;

    protected List<User> friends;
    protected List<FriendRequest> incomingRequests;
    protected List<FriendRequest> outgoingRequests;
    protected List<Post> posts;


    public User(String username, String password, String id, String fullName, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profile = new Profile();

        this.friends = new ArrayList<>();
        this.incomingRequests = new ArrayList<>();
        this.outgoingRequests = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.email = email;
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


    public List<FriendRequest> getIncomingRequests() {
        return incomingRequests;
    }

    public List<User> getFriends() {
        return friends;
    }

    public String getUsername() {
        return username;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}