package org.mypkg;

import java.util.*;

public class MediumStrategy implements DifficultyStrategy {
    private Random random = new Random();

    @Override
    public Problem generateProblem() {
        int number1 = random.nextInt(41) + 10; // Generate a number between 10 and 50
        int number2 = random.nextInt(41) + 10; // Generate a number between 10 and 50

        // List of operators
        List<String> operators = Arrays.asList("+", "-", "*", "/");
        // Select a random operator
        String operator = operators.get(random.nextInt(operators.size()));

        // If the operator is "/", ensure that the numerator is divisible by the denominator
        while (operator.equals("/") && number1 % number2 != 0) {
            number1 = random.nextInt(10) + 1;
            number2 = random.nextInt(10) + 1;
        }

        int answer;
        switch (operator) {
            case "+":
                answer = number1 + number2;
                break;
            case "-":
                answer = number1 - number2;
                break;
            case "*":
                answer = number1 * number2;
                break;
            case "/":
                // To avoid division by zero
                number2 = number2 == 0 ? 1 : number2;
                answer = number1 / number2;
                break;
            default:
                throw new IllegalStateException("Unexpected operator: " + operator);
        }

        String problem = number1 + " ? " + number2 + " = " + answer;
        return new Problem(problem, operator);
    }

    @Override
    public List<String> generateAnswers(Problem problem) {
        // The possible answers are the four arithmetic operators
        List<String> answers = Arrays.asList("+", "-", "*", "/");
        Collections.shuffle(answers);
        return answers;
    }
}