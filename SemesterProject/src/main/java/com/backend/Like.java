package com.backend;

import java.time.LocalDateTime;

public class Like {
    private User user;
    private LocalDateTime createdAt;

    public Like(User user) {
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}