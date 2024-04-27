package org.vut.ija_project.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.vut.ija_project.DataLayer.Common.Position;
import org.vut.ija_project.DataLayer.Environment.Room;
import org.vut.ija_project.DataLayer.Robot.ControlledRobot;

public class MainView extends BorderPane
{
    public MainView() {
        // Make canvas
        Canvas canvas = new Canvas(800, 600);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        this.setCenter(canvas);

        // Button for apps (start, pause, resume)
        Button startButton = new Button("Start");
        startButton.setStyle("-fx-base: #00ff00; ");

        Button pauseButton = new Button("Pause");
        pauseButton.setStyle("-fx-base: #ff0000;");

        Button resumeButton = new Button("Resume");
        resumeButton.setStyle("-fx-base: #000000; -fx-text-fill: #ffffff;");

        // -----------------------------------------------------------------------------
        // ----------- FORMS FOR SETTING DIMENSIONS AND ADDING ROBOT/OBSTACLE ---------
        // -----------------------------------------------------------------------------

        // Form for setting the dimensions of the environment
        Label titleLabel = new Label("Please set the dimensions of your environment:");
        titleLabel.setStyle("-fx-font-weight: bold;");
        Label widthLabel = new Label("Width:");
        TextField widthField = new TextField();
        Label heightLabel = new Label("Height:");
        TextField heightField = new TextField();
        Button setDimensionsButton = new Button("Set Dimensions");
        VBox dimensionForm = new VBox(10);
        dimensionForm.setPadding(new Insets(10));
        dimensionForm.getChildren().addAll(titleLabel, widthLabel, widthField, heightLabel, heightField, setDimensionsButton);

        // Form for adding robot on the canvas with the given position
        Label addRobotLabel = new Label("Please set the position of the robot:");
        addRobotLabel.setStyle("-fx-font-weight: bold;");
        Label xLabelR = new Label("X:");
        TextField xFieldR = new TextField();
        Label yLabelR = new Label("Y:");
        TextField yFieldR = new TextField();
        Button addRobotButton = new Button("Add Robot");
        VBox addRobotForm = new VBox(10);
        addRobotForm.setPadding(new Insets(10));
        addRobotForm.getChildren().addAll(addRobotLabel, xLabelR, xFieldR, yLabelR, yFieldR, addRobotButton);

        // Form for adding obstacle on the canvas with the given position
        Label addObstacleLabel = new Label("Please set the position of the obstacle:");
        addObstacleLabel.setStyle("-fx-font-weight: bold;");
        Label xLabelO = new Label("X:");
        TextField xFieldO = new TextField();
        Label yLabelO = new Label("Y:");
        TextField yFieldO = new TextField();
        Button addObstacleButton = new Button("Add Obstacle");
        VBox addObstacleForm = new VBox(10);
        addObstacleForm.setPadding(new Insets(10));
        addObstacleForm.getChildren().addAll(addObstacleLabel, xLabelO, xFieldO, yLabelO, yFieldO, addObstacleButton);

        // -----------------------------------------------------------------------------
        // ---------------------------- EVENT HANDLERS ---------------------------------
        // -----------------------------------------------------------------------------

        // For setting the dimensions of the environment
        setDimensionsButton.setOnAction(event -> {

        });

        // For adding robot on the canvas
        addRobotButton.setOnAction(event -> {
            });

        // For adding obstacle on the canvas
        addObstacleButton.setOnAction(event -> {

        });

        startButton.setOnAction(event -> {
        });

        pauseButton.setOnAction(event -> {
        });

        // For resetting the environment to its initial state
        resumeButton.setOnAction(event -> {

        });

        // Add the forms to the left side of the BorderPane
        VBox leftSide = new VBox(10);
        leftSide.getChildren().addAll(dimensionForm, addRobotForm, addObstacleForm);
        this.setLeft(leftSide);

        // Add the buttons to the top of the BorderPane
        HBox MainButtons = new HBox(10);
        MainButtons.getChildren().addAll(pauseButton, startButton, resumeButton);
        MainButtons.setAlignment(Pos.CENTER);
        this.setTop(MainButtons);
    }
}
