package org.mypkg;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

public class GameObject extends StackPane {
    private Text text;
    private Circle circle;

    public GameObject(String number) {
        this.text = new Text(number);
        this.circle = new Circle(30); // Create a circle with a radius of 20px

        // Set the fill color to white if the number is "?", otherwise set it to YELLOWGREEN
        if (number.equals("?")) {
            circle.setFill(Color.WHITE);
        }
        else if (number.equals("+")
                || number.equals("-")
                || number.equals("/")
                || number.equals("*")
                || number.equals("=")) {
            circle.setFill(Color.LIGHTBLUE);
        }
        else {
            circle.setFill(Color.YELLOWGREEN);
        }

        // Set the stroke color and width
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(1);

        this.text.setFont(new Font(20)); // Set the font size to 20

        // Add the circle and text to the StackPane
        getChildren().addAll(circle, this.text);
    }

    public String getNumber() {
        return text.getText();
    }

    public void setNumber(String number) {
        this.text.setText(number);
    }

    public Circle getCircle() {
        return circle;
    }
    public String getText() {
        return text.getText();
    }
}