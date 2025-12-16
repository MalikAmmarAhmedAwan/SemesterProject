package com.backend;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.Serializable;

public class Post implements Likable, Serializable {
    private static final long serialVersionUID = 1L;
    String author;
    String content;
    LocalDateTime date;
    private ArrayList<Like> likes;
    private ArrayList<Comments> comments;


    public Post(User user, String content) {
        this.author = user.getUsername();
        this.content = content;
        this.date = LocalDateTime.now();
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public Post(String username, String content) {
        this.author = username;
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

    public boolean hasLiked(User user) {
        for (Like l : this.likes) {
            if (l.getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }

    public int getCommentCount() {
        if (this.comments == null) {
            return 0;
        }
        return this.comments.size();    }
    public ArrayList<Comments> getComments() {
        return comments;
    }

    public String getContent() {
        return content;
    }

    public int getLikes() {
        if (this.likes == null) {
            this.likes = new ArrayList<>();
            return 0;
        }
        return this.likes.size();
    }

    public String getAuthor() {
        return author;
    }
}

