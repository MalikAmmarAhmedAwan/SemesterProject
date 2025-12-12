package com.backend;

public class DataLoader {

    public static void populateData() {
        AuthService auth = new AuthService();

        User ahmad = auth.register("ahmad", "ahmad123", "Ahmad Khan", "ahmad@example.com");
        User talha = auth.register("talha", "talha123", "Talha Ali", "talha@example.com");
        User moiz = auth.register("moiz", "moiz123", "Moiz Raza", "moiz@example.com");
        User ammar = auth.register("ammar", "ammar123", "Ammar Javed", "ammar@example.com");

        System.out.println("--- Users Registered ---");

        makeFriends(ahmad, talha);
        makeFriends(ahmad, moiz);
        makeFriends(talha, ammar);
        makeFriends(moiz, ammar);

        System.out.println("--- Friendships Connected ---");

        Post p1 = ahmad.createPost("Finally finished the Java project! ☕ #coding");
        Post p2 = ahmad.createPost("Who is up for cricket this weekend?");
        Post p3 = talha.createPost("Just watched the new movie at Emporium. Highly recommended!");
        Post p4 = moiz.createPost("Does anyone have notes for the DSA exam? Pls help.");
        Post p5 = ammar.createPost("Lahore weather is so unpredictable these days.");

        System.out.println("--- Posts Created ---");

        talha.makeComment(p1, "Great job Ahmad! Treat when?");
        moiz.makeComment(p1, "Congrats bro.");
        ammar.makeComment(p2, "I am in! Let's go to the park near my place.");
        ahmad.makeComment(p3, "No spoilers please, I haven't seen it yet!");
        ammar.makeComment(p4, "I have the PDF, sending it to you on WhatsApp.");

        System.out.println("--- Comments Created ---");
    }

    private static void makeFriends(User u1, User u2) {
        u1.getFriends().add(u2);
        u2.getFriends().add(u1);
    }
}