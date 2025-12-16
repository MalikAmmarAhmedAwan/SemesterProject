package com.backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class AuthService implements Serializable {
    private static final long serialVersionUID = 1L;
    private static ArrayList<User> registeredUsers = new ArrayList<>();


    public User register(String username, String password, String fullName, String email) {
        System.out.println("--- Starting Registration for: " + username + " ---");

        for (User u : registeredUsers) {
            System.out.println("Comparing against stored user: [" + u.getUsername() + "]");

            if (u.getUsername().equalsIgnoreCase(username)) {
                System.out.println("Error: Username '" + username + "' is already taken.");
                return null;
            }
        }
        String autoId = UUID.randomUUID().toString();
        User newUser = new User(username, password, autoId, fullName, email);
        registeredUsers.add(newUser);

        System.out.println("Registration successful. Total users: " + registeredUsers.size());
        return newUser;
    }

    public static ArrayList<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public User login(String username, String password) {
        for (User u : registeredUsers) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                if (u.getPassword().equals(password)) {
                    System.out.println("Login successful! Welcome, " + u.getUsername());
                    return u;
                } else {
                    System.out.println("Error: Incorrect password.");
                    return null;
                }
            }
        }
        System.out.println("Error: User not found.");
        return null;
    }

    public static void setRegisteredUsers(ArrayList<User> users) {
        registeredUsers = users;
    }
}