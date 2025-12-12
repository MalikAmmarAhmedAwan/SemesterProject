package com.backend;

public class Profile {
    private String fullName;
    private String bio;
    private String city;
    private String email;

    public Profile(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
        this.bio = "";
        this.city = "";
    }

    public Profile(String fullName, String email, String bio, String city) {
        this.fullName = fullName;
        this.email = email;
        this.bio = bio;
        this.city = city;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBio() {
        return bio;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}