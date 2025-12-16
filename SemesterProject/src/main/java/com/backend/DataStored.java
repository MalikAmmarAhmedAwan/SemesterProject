package com.backend;

import java.io.*;
import java.util.*;

public class DataStored {
    private static DataStored instance;
    private ArrayList<User> users;
    private List<Post> posts;
    private final String FILE_NAME = "social_network_data.dat";

    public DataStored() {
        this.users = new ArrayList<>();
        this.posts = new ArrayList<>();
        loadData();
    }

    public static DataStored getInstance() {
        if (instance == null) {
            instance = new DataStored();
        }
        return instance;
    }

    public User findUserByUsername(String username) {
        for (int i = 0; i < this.users.size(); i++) {
            User currentUser = this.users.get(i);
            if (currentUser.getUsername().equals(username)) {
                return currentUser;
            }
        }
        return null;
    }

    public void addUser(User u) {
        users.add(u);
        saveData();
    }

    public void addPost(Post p) {
        posts.add(p);
        saveData();
    }
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
            oos.writeObject(posts);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No save file found. Starting fresh.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            users = (ArrayList<User>) ois.readObject();
            posts = (List<Post>) ois.readObject();
            System.out.println("Data loaded successfully. Users: " + users.size() + ", Posts: " + posts.size());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public List<Post> getPosts() {
        return posts;
    }
}