package com.backend;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Comments implements Likable {
    private String id;
    private User author;
    private String content;
    private LocalDateTime createdAt;
    private List<Like> likes;

    public Comments(User author, String content) {
        this.id = UUID.randomUUID().toString();
        this.author = author;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.likes = new ArrayList<>();
    }

    @Override
    public void like(User user) {
        // 1. Check if user already liked this comment
        for (int i = 0; i < this.likes.size(); i++) {
            Like currentLike = this.likes.get(i);
            if (currentLike.getUser().equals(user)) {
                return;
            }
        }
        Like newLike = new Like(user);
        this.likes.add(newLike);
    }
    @Override
    public void unlike(User user) {
        for (int i = 0; i < this.likes.size(); i++) {
            Like currentLike = this.likes.get(i);
            if (currentLike.getUser().equals(user)) {
                this.likes.remove(i);
                return;
            }
        }
    }

    public String getContent() {
        return content;
    }
    public User getAuthor() {
        return author;
    }
    public List<Like> getLikes() {
        return likes;
    }
}