package com.backend;

import java.util.ArrayList;
import java.util.UUID;
public class AuthService {
    private static ArrayList<User> registeredUsers = new ArrayList<>();


    public User register(String username, String password, String fullName, String email) {
        for (int i = 0; i < registeredUsers.size(); i++) {
            User u = registeredUsers.get(i);

            if (u.getUsername().equals(username)) {
                System.out.println("Error: Username '" + username + "' is already taken.");
                return null;
            }
        }
        String autoId = UUID.randomUUID().toString();

        User newUser = new User(username, password, autoId, fullName, email);
        registeredUsers.add(newUser);
        System.out.println("Registration successful for: " + username);
        return newUser;
    }

    public User login(String username, String password) {
        for (int i = 0; i < registeredUsers.size(); i++) {
            User u = registeredUsers.get(i);

            if (u.getUsername().equals(username)) {
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
}