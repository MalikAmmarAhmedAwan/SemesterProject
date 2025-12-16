package com.backend;

import java.io.Serializable;

public class Profile implements Serializable {
    private static final long serialVersionUID = 1L;

    private String city;
    private String bio;
    private int numberOfPosts;

    public Profile() {
        this.city = "Unknown City";
        this.bio = "No bio yet.";
        this.numberOfPosts = 0;
    }

    public String getCity() { return city; }
    public String getBio() { return bio; }
    public int getNumberOfPosts() { return numberOfPosts; }

    public void setCity(String city) { this.city = city; }
    public void setBio(String bio) { this.bio = bio; }
}