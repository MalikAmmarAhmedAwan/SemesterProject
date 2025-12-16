package com.backend;

import java.time.LocalDateTime;
import java.io.Serializable;

public class Like implements Serializable {
    private static final long serialVersionUID = 1L;
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