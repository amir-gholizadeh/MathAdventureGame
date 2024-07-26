package org.mypkg;

public class ChallengeFactory {
    public DifficultyStrategy createDifficulty(String difficulty) {
        switch (difficulty) {
            case "easy":
                return new EasyStrategy();
            case "medium":
                return new MediumStrategy();
            case "hard":
                return new HardStrategy();
            default:
                throw new IllegalArgumentException("Invalid difficulty level: " + difficulty);
        }
    }
}