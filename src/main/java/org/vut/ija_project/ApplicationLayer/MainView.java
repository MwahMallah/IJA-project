package org.vut.ija_project.ApplicationLayer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Affine;
import org.vut.ija_project.BusinessLayer.EnvironmentManager;
import org.vut.ija_project.DataLayer.Common.Position;

public class MainView extends HBox
{
    private EnvironmentManager environmentManager;
    private int canvasWidth = 600;
    private int canvasHeight = 600;
    private VBox leftSide;
    private VBox rightSide;
    private HBox canvasHBox;

    private TextField xRobotPosField;
    private TextField yRobotPosField;

    private Canvas canvas;
    private GraphicsContext gc;
    private Affine affine;

    public MainView(EnvironmentManager environmentManager) {
        this.environmentManager = environmentManager;

        leftSide  = new VBox(10);
        rightSide = new VBox(10);

        AddCanvas();
        AddButtons();
        AddForms();

        HBox.setHgrow(leftSide, Priority.ALWAYS);
        HBox.setHgrow(rightSide, Priority.ALWAYS);
        leftSide.maxWidthProperty().bind(this.widthProperty().multiply(1/5f));
        rightSide.maxWidthProperty().bind(this.widthProperty().multiply(4/5f));
    }

    private void AddForms() {
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
        Label xLabelRobotPos = new Label("X:");
        xRobotPosField = new TextField();
        Label yLabelRobotPos = new Label("Y:");
        yRobotPosField = new TextField();
        Button addRobotButton = new Button("Add Robot");
        VBox addRobotForm = new VBox(10);
        addRobotForm.setPadding(new Insets(10));
        addRobotForm.getChildren().addAll(addRobotLabel, xLabelRobotPos,
                xRobotPosField, yLabelRobotPos, yRobotPosField, addRobotButton);

        // Form for adding obstacle on the canvas with the given position
        Label addObstacleLabel = new Label("Please set the position of the obstacle:");
        addObstacleLabel.setStyle("-fx-font-weight: bold;");
        Label xLabelObstaclePos = new Label("X:");
        TextField xObstaclePosField = new TextField();
        Label yLabelObstaclePos = new Label("Y:");
        TextField yObstaclePosField = new TextField();
        Button addObstacleButton = new Button("Add Obstacle");
        VBox addObstacleForm = new VBox(10);
        addObstacleForm.setPadding(new Insets(10));
        addObstacleForm.getChildren().addAll(addObstacleLabel, xLabelObstaclePos,
                xObstaclePosField, yLabelObstaclePos, yObstaclePosField, addObstacleButton);

        // For setting the dimensions of the environment
        setDimensionsButton.setOnAction(event -> {

        });

        // For adding robot on the canvas
        addRobotButton.setOnAction(event -> {
            AddRobotToCanvas();
        });

        // For adding obstacle on the canvas
        addObstacleButton.setOnAction(event -> {

        });

        // Add the forms to the left side of the BorderPane
        leftSide.getChildren().addAll(dimensionForm, addRobotForm, addObstacleForm);
        this.getChildren().addAll(leftSide, rightSide);
    }

    private void AddButtons() {
        // Button for apps (start, pause, resume)
        Button startButton = new Button("Start");
        startButton.setStyle("-fx-base: #00ff00; ");

        // Button for apps (start, pause, resume)
        Button stepButton = new Button("Step");
        stepButton.setStyle("-fx-base: #0000ff; ");

        Button pauseButton = new Button("Pause");
        pauseButton.setStyle("-fx-base: #ff0000;");

        Button resumeButton = new Button("Resume");
        resumeButton.setStyle("-fx-base: #000000; -fx-text-fill: #ffffff;");

        // Add the buttons to the top of the BorderPane
        HBox mainButtons = new HBox(10);
        mainButtons.getChildren().addAll(pauseButton, startButton, stepButton, resumeButton);
        mainButtons.setAlignment(Pos.CENTER);

        rightSide.getChildren().addAll(mainButtons, canvasHBox);

        // -----------------------------------------------------------------------------
        // ---------------------------- EVENT HANDLERS ---------------------------------
        // -----------------------------------------------------------------------------

        startButton.setOnAction(event -> {
        });

        pauseButton.setOnAction(event -> {
        });

        stepButton.setOnAction(event -> {
            environmentManager.SimulationStep();
            DrawObjects();
        });

        // For resetting the environment to its initial state
        resumeButton.setOnAction(event -> {
        });
    }


    private void AddCanvas() {
        affine = new Affine();
        // Make canvas
        canvas = new Canvas(canvasWidth, canvasHeight);
        canvasHBox = new HBox(10);

        affine.appendScale(canvasWidth / (float) environmentManager.getCols(),
                canvasHeight / (float) environmentManager.getRows());

        gc = canvas.getGraphicsContext2D();
        EraseObjects();

        canvasHBox.getChildren().add(canvas);
        canvasHBox.setAlignment(Pos.CENTER);
    }


    private void AddRobotToCanvas() {
        Integer x = Integer.parseInt(xRobotPosField.getText());
        Integer y = Integer.parseInt(yRobotPosField.getText());

        environmentManager.AddRobot(x, y, "Automated");
        DrawObjects();
    }

    private void EraseObjects() {
        gc.setTransform(new Affine());
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setLineWidth(0.2);
        for (int x = 0; x <= canvasWidth; x += canvasWidth / 10) {
            gc.strokeLine(x, 0, x, canvasHeight);
        }

        for (int y = 0; y <= canvasHeight; y += canvasHeight / 10) {
            gc.strokeLine(0, y, canvasWidth, y);
        }
    }

    private void DrawObjects() {
        var robots = environmentManager.GetRobots();
        EraseObjects();

        gc.setTransform(affine);
        gc.setFill(Color.BLACK);
        for (var robot : robots) {
            Position pos = robot.getPosition();
            int x = pos.getCol();
            int y = pos.getRow();

            gc.fillOval(x, y, 1, 1);
        }
    }
}
