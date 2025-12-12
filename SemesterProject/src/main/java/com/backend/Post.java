package com.backend;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Post implements Likable {
    String author;
    String content;
    LocalDateTime date;
    private ArrayList<Like> likes;
    private ArrayList<Comments> comments;

    public Post(String author, String content, String date) {
        this.author = author;
        this.content = content;
        this.date = LocalDateTime.parse(date);
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public Post(User user, String content) {
        this.author = user.getUsername();
        this.content = content;
        this.date = LocalDateTime.now();
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    @Override
    public void like(User user) {
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

    public void addComment(User user, String text) {
        Comments newComment = new Comments(user, text);
        this.comments.add(newComment);
    }

    public int getCommentCount() {
        return comments.size();
    }
    public ArrayList<Comments> getComments() {
        return comments;
    }

    public String getContent() {
        return content;
    }

    public int getLikes() {
        return likes.size();
    }

    public String getAuthor() {
        return author;
    }
}

