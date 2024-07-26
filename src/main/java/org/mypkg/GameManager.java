package org.mypkg;

public class GameManager {
    private static GameManager instance;

    private GameManager() {
        // Private constructor to prevent instantiation
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    // Add methods to control the game flow
}