package org.vut.ija_project.GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.vut.ija_project.Common.Position;
import org.vut.ija_project.Environment.Room;
import org.vut.ija_project.Robot.AutonomousRobot;
import org.vut.ija_project.Robot.ControlledRobot;
import org.vut.ija_project.Robot.Robot;

public class RobotSimulatorGUI extends Application {

    private Canvas canvas;
    private GraphicsContext gc;
    private Button addRobotButton;
    private Button addObstacleButton;
    private Button startButton;
    private Button pauseButton;
    private Button resumeButton;
    private TextField widthField;
    private TextField heightField;
    private Room room;
    private Robot robot;
    private TextField xField;
    private TextField yField;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Robot Simulator");

        BorderPane root = new BorderPane();

        // Make canvas
        canvas = new Canvas(800, 600);
        room = Room.create(800, 600);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        root.setCenter(canvas);

        // Button for apps (start, pause, resume)
        startButton = new Button("Start");
        startButton.setStyle("-fx-base: #00ff00; ");

        pauseButton = new Button("Pause");
        pauseButton.setStyle("-fx-base: #ff0000;");

        resumeButton = new Button("Resume");
        resumeButton.setStyle("-fx-base: #000000; -fx-text-fill: #ffffff;");

        // -----------------------------------------------------------------------------
        // ----------- FORMS FOR SETTING DIMENSIONS AND ADDING ROBOT/OBSTACLE ---------
        // -----------------------------------------------------------------------------

        // Form for setting the dimensions of the environment
        Label titleLabel = new Label("Please set the dimensions of your environment:");
        titleLabel.setStyle("-fx-font-weight: bold;");
        Label widthLabel = new Label("Width:");
        widthField = new TextField();
        Label heightLabel = new Label("Height:");
        heightField = new TextField();
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
            // Získání rozměrů z textových polí
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            room = Room.create(width, height);
            // Vymazání plátna
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            // Vykreslení prostředí
            gc.setFill(Color.LIGHTGRAY);
            gc.fillRect(0, 0, width, height);
        });

        // For adding robot on the canvas
        addRobotButton.setOnAction(event -> {
            if (room == null) {
                return;
            }
            // Read the position from the text fields
            int x = Integer.parseInt(xFieldR.getText());
            int y = Integer.parseInt(yFieldR.getText());
            // Create the robot
            Robot robot = ControlledRobot.create(room, new Position(x, y));
            room.addRobot(robot);
            // Draw the robot
            gc.setFill(Color.BLUE);
            gc.fillOval(x, y, 10, 10);
        });

        // For adding obstacle on the canvas
        addObstacleButton.setOnAction(event -> {
            if (room == null) {
                return;
            }
            // Read the position from the text fields
            int x = Integer.parseInt(xFieldO.getText());
            int y = Integer.parseInt(yFieldO.getText());
            // Create the obstacle
            room.createObstacleAt(x, y);
            // Draw the obstacle
            gc.setFill(Color.RED);
            gc.fillRect(x, y, 10, 10);
        });

        startButton.setOnAction(event -> {
        });

        pauseButton.setOnAction(event -> {
        });

        // For resetting the environment to its initial state
        resumeButton.setOnAction(event -> {
            // Clear the canvas
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            // Reset the room
            room = null;
        });


        // -----------------------------------------------------------------------------
        // ---------------------------- LAYOUT -----------------------------------------
        // -----------------------------------------------------------------------------

        // Add the forms to the left side of the BorderPane
        VBox leftSide = new VBox(10);
        leftSide.getChildren().addAll(dimensionForm, addRobotForm, addObstacleForm);
        root.setLeft(leftSide);

        // Add the buttons to the top of the BorderPane
        HBox MainButtons = new HBox(10);
        MainButtons.getChildren().addAll(pauseButton, startButton, resumeButton);
        MainButtons.setAlignment(Pos.CENTER);
        root.setTop(MainButtons);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
