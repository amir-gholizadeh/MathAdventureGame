package org.mypkg;

public class Problem {
    private String problem;
    private String answer;
    private String secondAnswer; // This is the optional third parameter

    // Constructor for two parameters
    public Problem(String problem, String answer) {
        this.problem = problem;
        this.answer = answer;
        this.secondAnswer = null; // By default, the second answer is null
    }

    // Constructor for three parameters
    public Problem(String problem, String answer, String secondAnswer) {
        this.problem = problem;
        this.answer = answer;
        this.secondAnswer = secondAnswer;
    }

    public String getProblem() {
        return problem;
    }

    public String getAnswer() {
        return answer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }
}