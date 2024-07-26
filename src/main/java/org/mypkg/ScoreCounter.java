package org.mypkg;

public class ScoreCounter implements Observer {
    private int score = 0;
    private PlayerProgress playerProgress;

    public ScoreCounter() {
        this.playerProgress = new PlayerProgress();
    }

    @Override
    public void update(boolean correctAnswer) {
        if (correctAnswer) {
            score++;
            playerProgress.updateHighestScore(score);
        } else {
            score = 0;
        }
    }

    public int getScore() {
        return score;
    }

    public int getHighestScore() {
        return playerProgress.getHighestScore();
    }
}