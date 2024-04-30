package org.vut.ija_project.ApplicationLayer;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.vut.ija_project.BusinessLayer.EnvironmentManager;

public class FormsView extends VBox {
    private EnvironmentManager environmentManager;
    private TextField xRobotPosField;
    private TextField yRobotPosField;
    private TextField xObstaclePosField;
    private TextField yObstaclePosField;

    public FormsView(EnvironmentManager environmentManager) {
        this.environmentManager = environmentManager;
        this.setSpacing(10);

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
        xObstaclePosField = new TextField();
        Label yLabelObstaclePos = new Label("Y:");
        yObstaclePosField = new TextField();
        Button addObstacleButton = new Button("Add Obstacle");
        VBox addObstacleForm = new VBox(10);
        addObstacleForm.setPadding(new Insets(10));
        addObstacleForm.getChildren().addAll(addObstacleLabel, xLabelObstaclePos,
                xObstaclePosField, yLabelObstaclePos, yObstaclePosField, addObstacleButton);

        // For setting the dimensions of the environment
        setDimensionsButton.setOnAction(event -> {});
        // For adding robot on the canvas
        addRobotButton.setOnAction(event -> {addRobot();});
        // For adding obstacle on the canvas
        addObstacleButton.setOnAction(event -> {addObstacle();});

        this.getChildren().addAll(dimensionForm, addRobotForm, addObstacleForm);
    }

    private void addObstacle() {
        double x = Double.parseDouble(xObstaclePosField.getText());
        double y = Double.parseDouble(yObstaclePosField.getText());

        environmentManager.addObstacle(y, x);
    }

    private void addRobot() {
        double x = Double.parseDouble(xRobotPosField.getText());
        double y = Double.parseDouble(yRobotPosField.getText());

        environmentManager.addRobot(y, x, "Automated");
    }
}
