package org.vut.ija_project.ApplicationLayer;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.vut.ija_project.BusinessLayer.EnvironmentManager;

public class FormsView extends VBox {
    private EnvironmentManager environmentManager;
    private TextField xRobotPosField;
    private TextField yRobotPosField;
    private TextField xObstaclePosField;
    private TextField yObstaclePosField;
    private ComboBox<String> robotVariantList;

    public FormsView(EnvironmentManager environmentManager) {
        this.environmentManager = environmentManager;
        this.setSpacing(10);

        // -----------------------------------------------------------------------------
        // ----------- FORMS FOR SETTING DIMENSIONS AND ADDING ROBOT/OBSTACLE ---------
        // -----------------------------------------------------------------------------
        // Form for setting the dimensions of the environment
        Label titleLabel = new Label("Set the dimensions of your environment:");
        titleLabel.setStyle("-fx-font-weight: bold;");
        Label widthLabel = new Label("Width:");
        TextField widthField = new TextField();
        widthField.setMaxSize(80, 10);
        Label heightLabel = new Label("Height:");
        TextField heightField = new TextField();
        heightField.setMaxSize(80, 10);
        Button setDimensionsButton = new Button("Set Dimensions");
        VBox dimensionForm = new VBox(10);
        dimensionForm.setPadding(new Insets(10));
        dimensionForm.getChildren().addAll(titleLabel, widthLabel, widthField, heightLabel, heightField, setDimensionsButton);

        // Form for adding robot on the canvas with the given position
        Label addRobotLabel = new Label("Set the position of the bug:");
        addRobotLabel.setStyle("-fx-font-weight: bold;");
        Label xLabelRobotPos = new Label("X:");
        xRobotPosField = new TextField();
        xRobotPosField.setMaxSize(80, 10);
        Label yLabelRobotPos = new Label("Y:");
        yRobotPosField = new TextField();
        yRobotPosField.setMaxSize(80, 10);
        HBox addRobotButtonHbox = new HBox(10);
        Button addRobotButton = new Button("Add Bug");
        robotVariantList = new ComboBox<>();
        robotVariantList.getItems().addAll("Autonomous", "Controllable");
        robotVariantList.getSelectionModel().select("Autonomous");
        addRobotButtonHbox.getChildren().addAll(addRobotButton, robotVariantList);

        VBox addRobotForm = new VBox(10);
        addRobotForm.setPadding(new Insets(10));
        addRobotForm.getChildren().addAll(addRobotLabel, xLabelRobotPos,
                xRobotPosField, yLabelRobotPos, yRobotPosField, addRobotButtonHbox);

        // Form for adding obstacle on the canvas with the given position
        Label addObstacleLabel = new Label("Set the position of the obstacle:");
        addObstacleLabel.setStyle("-fx-font-weight: bold;");
        Label xLabelObstaclePos = new Label("X:");
        xObstaclePosField = new TextField();
        xObstaclePosField.setMaxSize(80, 10);
        Label yLabelObstaclePos = new Label("Y:");
        yObstaclePosField = new TextField();
        yObstaclePosField.setMaxSize(80, 10);
        Button addObstacleButton = new Button("Add Obstacle");
        VBox addObstacleForm = new VBox(10);
        addObstacleForm.setPadding(new Insets(10));
        addObstacleForm.getChildren().addAll(addObstacleLabel, xLabelObstaclePos,
                xObstaclePosField, yLabelObstaclePos, yObstaclePosField, addObstacleButton);

        // For setting the dimensions of the environment
        setDimensionsButton.setOnAction(event -> {});
        // For adding robot on the canvas
        addRobotButton.setOnAction(event -> addRobot());
        // For adding obstacle on the canvas
        addObstacleButton.setOnAction(event -> addObstacle());

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

        String type = robotVariantList.getSelectionModel().getSelectedItem();
        environmentManager.addRobot(y, x, type);
    }
}
