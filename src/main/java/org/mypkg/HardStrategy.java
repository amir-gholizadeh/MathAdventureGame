package org.mypkg;

import java.util.*;

public class HardStrategy implements DifficultyStrategy {
    private Random random = new Random();
    private List<String> operators = Arrays.asList("+", "-", "*", "/");

    @Override
    public Problem generateProblem() {
        Stack<Integer> stack = new Stack<>();
        StringBuilder problemBuilder = new StringBuilder();
        List<String> nonMissingOperators = Arrays.asList("+", "-");
        String missingOperator = operators.get(random.nextInt(operators.size()));
        String nonMissingOperator = nonMissingOperators.get(random.nextInt(nonMissingOperators.size()));

        // Generate the first number
        int number1 = random.nextInt(9) + 2; // Adjusted range
        stack.push(number1);

        // Generate the first operator and the second number
        int number2;
        if (missingOperator.equals("/")) {
            do {
                number2 = random.nextInt(9) + 2; // Adjusted range
            } while (number1 % number2 != 0);
        } else {
            number2 = random.nextInt(9) + 2; // Adjusted range
        }
        stack.push(number2);

        // Generate the second operator and the third number
        int number3 = random.nextInt(9) + 2; // Adjusted range
        stack.push(number3);

        // Calculate the result of the problem
        int result = calculateResult(number1, number2, missingOperator);
        result = calculateResult(result, number3, nonMissingOperator);

        // Build the problem string
        problemBuilder.append(number1).append(" ? ").append(number2).append(" ").append(nonMissingOperator).append(" ").append(number3).append(" = ").append(result);

        return new Problem(problemBuilder.toString(), missingOperator);
    }

    private int calculateResult(int number1, int number2, String operator) {
        switch (operator) {
            case "+":
                return number1 + number2;
            case "-":
                return number1 - number2;
            case "*":
                return number1 * number2;
            case "/":
                return number1 / number2;
            default:
                throw new IllegalStateException("Unexpected operator: " + operator);
        }
    }

    @Override
    public List<String> generateAnswers(Problem problem) {
        // The possible answers are the four arithmetic operators
        List<String> answers = Arrays.asList("+", "-", "*", "/");
        Collections.shuffle(answers);
        return answers;
    }
}