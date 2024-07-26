package org.mypkg;

import java.util.List;

public interface DifficultyStrategy {
    Problem generateProblem();
    List<String> generateAnswers(Problem problem);
}

