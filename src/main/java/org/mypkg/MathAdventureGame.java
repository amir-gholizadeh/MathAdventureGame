package org.mypkg;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.FadeTransition;


import java.util.ArrayList;
import java.util.List;

public class MathAdventureGame extends Application implements Subject {
    private ChallengeFactory challengeFactory = new ChallengeFactory();
    private Button easyButton;
    private Button mediumButton;
    private Button hardButton;
    private String correctAnswer;

    // Add a ScoreCounter field
    private Label scoreLabel;
    private Stage stage;
    private String currentDifficulty;
    private ScoreCounter scoreCounter = new ScoreCounter();
    private Label highestScoreLabel;



    private List<Observer> observers = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }


    //Implement the Subject interface methods
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(boolean correctAnswer) {
        for (Observer observer : observers) {
            observer.update(correctAnswer);
        }

        // Update the score label
        scoreLabel.setText("Score: " + scoreCounter.getScore());

        // If the answer was correct, start a new game with the same difficulty level
        if (correctAnswer) {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> startGame(this.currentDifficulty, this.stage));
            pause.play();
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;

        // Create a label to display the highest score
        highestScoreLabel = new Label("Highest Score: " + scoreCounter.getHighestScore());
        highestScoreLabel.setFont(new Font(15)); // Set the font size to 20

        // Create a VBox for the highestScoreLabel and align it to the top
        VBox highestScoreBox = new VBox(highestScoreLabel);
        highestScoreBox.setAlignment(Pos.TOP_CENTER);

        // Set the Vgrow property for the highestScoreLabel
        VBox.setVgrow(highestScoreLabel, Priority.ALWAYS);

        // Create a new BorderPane
        BorderPane borderPane = new BorderPane();

        // Set the highestScoreBox to the top of the BorderPane
        borderPane.setTop(highestScoreBox);

        HBox hbox = new HBox(10); // Create a HBox with 10px spacing
        hbox.setAlignment(Pos.CENTER); // Align the buttons in the center of the HBox

        Label label = new Label("Please select the difficulty level"); // Create a new label
        label.setFont(new Font(20)); // Set the font size to 20

        VBox vbox = new VBox(30, label, hbox); // Add the scoreLabel to the VBox
        vbox.setAlignment(Pos.CENTER); // Align the VBox in the center of the screen

        // Create buttons for the difficulty levels
        easyButton = new Button("Easy");
        easyButton.setMinSize(100, 100); // Set the size of the button
        easyButton.setOnAction(e -> startGame("easy", primaryStage));

        mediumButton = new Button("Medium");
        mediumButton.setMinSize(100, 100); // Set the size of the button
        mediumButton.setOnAction(e -> startGame("medium", primaryStage));

        hardButton = new Button("Hard");
        hardButton.setMinSize(100, 100); // Set the size of the button
        hardButton.setOnAction(e -> startGame("hard", primaryStage));

        hbox.getChildren().addAll(easyButton, mediumButton, hardButton);

        // Set the VBox to the center of the BorderPane
        borderPane.setCenter(vbox);

        Scene scene = new Scene(borderPane, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void startGame(String difficulty, Stage stage) {
        this.currentDifficulty = difficulty;

        DifficultyStrategy difficultyStrategy = challengeFactory.createDifficulty(difficulty);

        // Update the highest score label
        highestScoreLabel.setText("Highest Score: " + scoreCounter.getHighestScore());

        // Create a label to display the score
        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(new Font(20)); // Set the font size to 20

        // register the ScoreCounter as an observer
        observers.clear();
        registerObserver(scoreCounter);

        // Update the score label
        scoreLabel.setText("Score: " + scoreCounter.getScore());

        // Generate a problem
        Problem problem = difficultyStrategy.generateProblem();
        correctAnswer = problem.getAnswer();

        // Generate answers for the same problem
        List<String> answers = difficultyStrategy.generateAnswers(problem);

        // Create GameObjects for the problem parts
        String[] parts = problem.getProblem().split(" ");
        GameObject part1 = new GameObjectFactory().createGameObject(parts[0]);
        GameObject operator1 = new GameObjectFactory().createGameObject(parts[1]);
        GameObject part2 = new GameObjectFactory().createGameObject(parts[2]);
        GameObject equals;
        GameObject result;
        GameObject operator2 = null;
        GameObject part3 = null;

        // If the problem has three numbers and two operators, create additional GameObjects
        if (parts.length > 5) {
            operator2 = new GameObjectFactory().createGameObject(parts[3]);
            part3 = new GameObjectFactory().createGameObject(parts[4]);
            equals = new GameObjectFactory().createGameObject(parts[5]);
            result = new GameObjectFactory().createGameObject(parts[6]);
        } else {
            equals = new GameObjectFactory().createGameObject(parts[3]);
            result = new GameObjectFactory().createGameObject(parts[4]);
        }

        // Create a fade-in transition for each GameObject
        FadeTransition fadeInPart1 = createFadeInTransition(part1);
        FadeTransition fadeInOperator1 = createFadeInTransition(operator1);
        FadeTransition fadeInPart2 = createFadeInTransition(part2);
        FadeTransition fadeInEquals = createFadeInTransition(equals);
        FadeTransition fadeInResult = createFadeInTransition(result);

        // If the problem has three numbers and two operators, create additional fade-in transitions
        FadeTransition fadeInOperator2 = null;
        FadeTransition fadeInPart3 = null;
        if (parts.length > 5) {
            fadeInOperator2 = createFadeInTransition(operator2);
            fadeInPart3 = createFadeInTransition(part3);
        }

        // Start the fade-in transitions
        fadeInPart1.play();
        fadeInOperator1.play();
        fadeInPart2.play();
        fadeInEquals.play();
        fadeInResult.play();
        if (parts.length > 5) {
            fadeInOperator2.play();
            fadeInPart3.play();
        }

        // Create a drop zone for the missing part
        GameObject dropZone = new GameObjectFactory().createGameObject("?");
        dropZone.setOnDragOver(this::handleDragOver);
        dropZone.setOnDragDropped(this::handleDragDropped);

        // Replace the missing part with the drop zone
        if (operator1.getText().equals("?")) {
            operator1 = dropZone;
        }
        if (operator2 != null && operator2.getText().equals("?")) {
            operator2 = dropZone;
        }

        // Create a fade-in transition for the drop zone
        FadeTransition fadeInDropZone = createFadeInTransition(dropZone);
        fadeInDropZone.play();

        // Create the problem display
        HBox problemDisplay;
        if (parts.length > 5) {
            problemDisplay = new HBox(10, part1, operator1, part2, operator2, part3, equals, result);
        } else {
            problemDisplay = new HBox(10, part1, operator1, part2, equals, result);
        }
        problemDisplay.setAlignment(Pos.CENTER); // Align the problem in the center of the screen

        // Create draggable GameObjects for the answers
        HBox answerDisplay = new HBox(10);
        answerDisplay.setAlignment(Pos.CENTER);
        for (String answer : answers) {
            GameObject answerObject = new GameObjectFactory().createGameObject(answer);
            answerObject.setOnDragDetected(this::handleDragDetected);

            // Create a fade-in transition for each answer GameObject
            FadeTransition fadeInAnswer = createFadeInTransition(answerObject);
            fadeInAnswer.play();

            answerDisplay.getChildren().add(answerObject);
        }

        Label label = new Label("Drag and Drop the correct answer"); // Create a new label
        label.setFont(new Font(10)); // Set the font size to 20

        // Create a new BorderPane
        BorderPane borderPane = new BorderPane();

        // Create a VBox for the highestScoreLabel and align it to the top
        VBox highestScoreBox = new VBox(highestScoreLabel);
        highestScoreBox.setAlignment(Pos.TOP_CENTER);

        // Set the Vgrow property for the highestScoreLabel
        VBox.setVgrow(highestScoreLabel, Priority.ALWAYS);

        // Set the highestScoreBox to the top of the BorderPane
        borderPane.setTop(highestScoreBox);

        // Create the VBox without the backButton
        VBox vbox = new VBox(30, scoreLabel, problemDisplay, answerDisplay, label);
        vbox.setAlignment(Pos.CENTER); // Align the VBox to the center

        // Set the VBox to the center of the BorderPane
        borderPane.setCenter(vbox);

        // Create the backButton
        Button backButton = new Button("Back To Home");
        backButton.setOnAction(e -> {
            try {
                start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        // Create a HBox to place the backButton at the right
        HBox hbox = new HBox(backButton);
        hbox.setAlignment(Pos.BOTTOM_RIGHT); // Align the backButton to the right
        backButton.setMinSize(100, 50);
        HBox.setMargin(backButton, new javafx.geometry.Insets(25, 25, 25, 25));

        // Set the HBox to the bottom of the BorderPane
        borderPane.setBottom(hbox);

        // Create a new scene and set it on the stage
        Scene scene = new Scene(borderPane, 600, 600);
        stage.setScene(scene);
    }


    private void handleDragDetected(MouseEvent event) {
        // Cast the source of the event to a GameObject
        GameObject source = (GameObject) event.getSource();

        // Create a ClipboardContent, put the source number in it, and put it in the dragboard
        ClipboardContent content = new ClipboardContent();
        content.putString(source.getNumber());

        // Start a drag and drop gesture
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);
        db.setContent(content);

        // Create a snapshot of the source node and set it as the drag view
        db.setDragView(source.snapshot(null, null));

        // Set the x and y offset of the drag view image from the mouse cursor
        db.setDragViewOffsetX(30);
        db.setDragViewOffsetY(30);

        event.consume();
    }

    private void handleDragOver(DragEvent event) {
        // Allow the dragboard to accept the drag
        if (event.getGestureSource() != event.getGestureTarget() && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }

        event.consume();
    }

    private void handleDragDropped(DragEvent event) {
        // Get the dragboard
        Dragboard db = event.getDragboard();
        boolean success = false;

        // If the dragboard has a string, set the number of the target to the string
        if (db.hasString()) {
            GameObject target = (GameObject) event.getGestureTarget();
            target.setNumber(db.getString());
            success = true;

            // Check if the dropped answer is correct and provide feedback
            if (db.getString().equals(correctAnswer)) {
                target.getCircle().setFill(Color.GREEN);

                // Notify the observers of the correct answer
                notifyObservers(true);
            } else {
                target.getCircle().setFill(Color.RED);

                // Notify the observers of the incorrect answer
                notifyObservers(false);
            }
        }

        // Let the source know whether the string was successfully transferred and used
        event.setDropCompleted(success);

        event.consume();
    }

    // Create a fade-in transition for a GameObject
    private FadeTransition createFadeInTransition(GameObject gameObject) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), gameObject);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        return fadeIn;
    }
}