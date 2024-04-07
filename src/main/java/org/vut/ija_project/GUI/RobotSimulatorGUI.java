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

public class RobotSimulatorGUI extends Application {

    private Canvas canvas;
    private GraphicsContext gc;
    private Button addRobotButton;
    private Button addObstacleButton;
    private Button startButton;
    private Button pauseButton;
    private Button resumeButton;
    private Button rewindButton;
    private TextField widthField;
    private TextField heightField;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Robot Simulator");

        BorderPane root = new BorderPane();

        // Vytvoření plátna pro vykreslování prostředí
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        root.setCenter(canvas);

        // Form pro zadání rozměrů prostředí
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

        // Ovládací tlačítka
        addRobotButton = new Button("Add Robot");
        addObstacleButton = new Button("Add Obstacle");
        HBox addButtonsBox = new HBox(10);
        addButtonsBox.setPadding(new Insets(10));
        addButtonsBox.getChildren().addAll(addRobotButton, addObstacleButton);

        startButton = new Button("Start");
        pauseButton = new Button("Pause");
        resumeButton = new Button("Resume");
        rewindButton = new Button("Rewind");
        HBox controlButtonsBox = new HBox(10);
        controlButtonsBox.setPadding(new Insets(10));
        controlButtonsBox.getChildren().addAll(startButton, pauseButton, resumeButton, rewindButton);

        // Spojení všech prvků do jednoho layoutu
        VBox leftBox = new VBox(10);
        leftBox.getChildren().addAll(dimensionForm, addButtonsBox);
        VBox.setVgrow(dimensionForm, Priority.ALWAYS);

        root.setLeft(leftBox);
        root.setBottom(controlButtonsBox);

        // Událostní posluchač pro tlačítko Set Dimensions
        setDimensionsButton.setOnAction(event -> {
            // Získání rozměrů z textových polí
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            // Vymazání plátna
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            // Vykreslení prostředí
            gc.setFill(Color.LIGHTGRAY);
            gc.fillRect(0, 0, width, height);
        });

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);

        // Přizpůsobení velikosti ovládacích prvků při změně velikosti okna
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            leftBox.setPrefWidth(newVal.doubleValue() * 0.3);
        });

        primaryStage.show();
    }
}