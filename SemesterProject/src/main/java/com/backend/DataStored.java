package com.backend;

import java.util.ArrayList;
import java.util.List;

public class DataStored {
    private List<User> users;
    private List<Post> posts;

    public DataStored() {
        this.users = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public void saveUser(User user) {
        this.users.add(user);
    }

    public User findUserByUsername(String username) {
        for (int i = 0; i < this.users.size(); i++) {
            User currentUser = this.users.get(i);
            if (currentUser.getUsername().equals(username)) {
                return currentUser;
            }
        }
        return null;
    }

    public void savePost(Post post) {
        this.posts.add(post);
    }

    public List<Post> getAllPosts() {
        return this.posts;
    }
}