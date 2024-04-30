package org.vut.ija_project.ApplicationLayer.SelectedView;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.vut.ija_project.ApplicationLayer.Canvas.RobotView;
import org.vut.ija_project.BusinessLayer.EnvironmentManager;
import org.vut.ija_project.Common.ObjectConfiguration;
import org.vut.ija_project.DataLayer.Robot.Robot;

import java.util.Locale;

public class SelectedViewRobot extends SelectedView {
    private EnvironmentManager environmentManager;
    private final RobotView robotView;
    private TextField fieldSetX;
    private TextField fieldSetY;
    private TextField fieldSetVelocity;
    private TextField fieldSetAngle;
    private TextField fieldSetRotationAngle;
    private Button updateButton;
    private Button deleteButton;

    public SelectedViewRobot(RobotView robotView, EnvironmentManager environmentManager) {
        this.robotView = robotView;
        this.environmentManager = environmentManager;
    }
    @Override
    public void update() {
        this.getChildren().clear();
        createNewInfoLabel("Autonomous Bug", 100, 24);
        createNewInfoLabel("Current Position: " + getPosition(), 0, 18);
        createNewInfoLabel("Current Angle: " + robotView.getRobot().angle(), 0, 18);
        createNewInfoLabel("Current Velocity: " + robotView.getRobot().getVelocity(), 0, 18);
        createNewInfoLabel("Current Rotation Angle: " + robotView.getRobot().getRotationAngle(),
                100, 18);

        fieldSetX = createNewSetField("Set x: ", getX());
        fieldSetY = createNewSetField("Set y: ", getY());
        fieldSetAngle = createNewSetField("Set angle: ",
                Double.toString(robotView.getRobot().angle()));
        fieldSetVelocity = createNewSetField("Set velocity: ",
                Double.toString(robotView.getRobot().getVelocity()));
        fieldSetRotationAngle = createNewSetField("Set rotation angle: ",
                Double.toString(robotView.getRobot().getRotationAngle()));

        HBox.setMargin(fieldSetRotationAngle, new Insets(-7, 5, 70, 0));
        addButtons();

        deleteButton.setOnAction(this::handleDelete);
        updateButton.setOnAction(this::handleUpdate);
    }

    private void handleUpdate(ActionEvent actionEvent) {
        var newX = Double.parseDouble(fieldSetX.getText());
        var newY = Double.parseDouble(fieldSetY.getText());
        var newAngle = (int) Double.parseDouble(fieldSetAngle.getText());
        var newVelocity = Double.parseDouble(fieldSetVelocity.getText());
        var newRotationAngle = (int) Double.parseDouble(fieldSetRotationAngle.getText());
        Robot robot = robotView.getRobot();

        //if there is no changes don't update anything
        if (newX == robot.getPosition().getX() && newY == robot.getPosition().getY()
            && newAngle == robot.angle() && newVelocity == robot.getVelocity()
            && newRotationAngle == robot.getRotationAngle()) return;

        //TODO: update robot in environment
        var configuration = new ObjectConfiguration(newX, newY, newAngle, newVelocity, newRotationAngle);
        environmentManager.updateRobot(robotView.getRobot(), configuration);
    }

    private void handleDelete(ActionEvent actionEvent) {
        environmentManager.deleteRobot(robotView.getRobot());
    }

    private String getX() {
        double x = robotView.getRobot().getPosition().getX();
        return String.format(Locale.US, "%.2f", x);
    }

    private String getY() {
        double y = robotView.getRobot().getPosition().getY();
        return String.format(Locale.US, "%.2f", y);
    }

    private String getPosition() {
        return String.format(getX() + " : " + getY());
    }

    private void createNewInfoLabel(String labelText, int bottomMargin, int fontSize) {
        Label infoLabel = new Label(labelText);
        infoLabel.setFont(new Font("Arial", fontSize));
        VBox.setMargin(infoLabel, new Insets(0, 0, bottomMargin, 15));

        this.getChildren().addAll(infoLabel);
    }

    private TextField createNewSetField(String fieldText, String fieldPlaceholder) {
        HBox setProperty = new HBox(10);
        VBox.setMargin(setProperty, new Insets(0, 0, 0, 15)); // Adds 15px right margin

        Label labelSetProperty = new Label(fieldText);
        labelSetProperty.setFont(new Font("Arial", 18));

        TextField fieldSetProperty = new TextField();
        HBox.setMargin(fieldSetProperty, new Insets(-7, 5, 15, 0));

        fieldSetProperty.setText(fieldPlaceholder);
        fieldSetProperty.setFont(new Font("Arial", 18));
        fieldSetProperty.setMaxSize(80, 5);

        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS); // Allow spacer to take all available space

        setProperty.getChildren().addAll(labelSetProperty, spacer, fieldSetProperty);
        this.getChildren().addAll(setProperty);

        return fieldSetProperty;
    }

    private void addButtons() {
        VBox buttonsBox = new VBox(20);
        buttonsBox.setAlignment(Pos.CENTER);
        updateButton = new Button("Update");
        updateButton.setFont(new Font("Arial", 20));
        updateButton.setStyle("-fx-base: #1e90ff; -fx-text-fill: white;");

        deleteButton = new Button("Delete");
        deleteButton.setFont(new Font("Arial", 20));
        deleteButton.setStyle("-fx-base: #ff301e; -fx-text-fill: white;");
        buttonsBox.getChildren().addAll(updateButton, deleteButton);

        this.getChildren().addAll(buttonsBox);
    }
}
