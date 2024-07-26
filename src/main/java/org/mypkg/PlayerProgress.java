package org.mypkg;

public class PlayerProgress {
    private int highestScore;

    public PlayerProgress() {
        this.highestScore = 0;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void updateHighestScore(int newScore) {
        if (newScore > highestScore) {
            highestScore = newScore;
        }
    }
}