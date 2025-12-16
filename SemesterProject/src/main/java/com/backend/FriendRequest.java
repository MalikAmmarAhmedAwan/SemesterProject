package com.backend;

import java.io.Serializable;

public class FriendRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private User from;
    private User to;
    private FriendRequestStatus status;

    public FriendRequest(User from, User to) {
        this.from = from;
        this.to = to;
        this.status = FriendRequestStatus.PENDING;
    }

    public void accept() {
        this.status = FriendRequestStatus.ACCEPTED;
    }

    public void reject() {
        this.status = FriendRequestStatus.REJECTED;
    }

    public User getFrom() { return from; }
    public User getTo() { return to; }
    public FriendRequestStatus getStatus() { return status; }
}