package org.vut.ija_project.ApplicationLayer;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.vut.ija_project.BusinessLayer.EnvironmentManager;
import org.vut.ija_project.BusinessLayer.Simulator;

public class ButtonsView extends HBox {
    private EnvironmentManager environmentManager;
    private Simulator simulator;
    private Button startButton;
    private Button pauseButton;
    /**
     * Creates an {@code HBox} layout with {@code spacing = 0}.
     */
    public ButtonsView(EnvironmentManager environmentManager) {
        this.environmentManager = environmentManager;
        this.simulator = environmentManager.getSimulator();

        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);

        this.startButton = new Button("Start");
        this.startButton.setStyle("-fx-base: #00ff00; ");

        // Button for apps (start, pause, resume)
        Button stepButton = new Button("Step");
        stepButton.setStyle("-fx-base: #0000ff; ");

        this.pauseButton = new Button("Pause");
        this.pauseButton.setStyle("-fx-base: #ff0000;");
        this.pauseButton.setDisable(true);

        Button resetButton = new Button("Reset");
        resetButton.setStyle("-fx-base: #000000; -fx-text-fill: #ffffff;");

        this.getChildren().addAll(pauseButton, startButton, stepButton, resetButton);
        // -----------------------------------------------------------------------------
        // ---------------------------- EVENT HANDLERS ---------------------------------
        // -----------------------------------------------------------------------------
        startButton.setOnAction(event -> {playSimulation();});
        pauseButton.setOnAction(event -> {pauseSimulation();});
        stepButton.setOnAction(event -> {environmentManager.simulationStep();});
        resetButton.setOnAction(event -> {resetSimulation();});
    }

    private void resetSimulation() {
        simulator.stop();
        environmentManager.simulationReset();

        this.pauseButton.setDisable(true);
        this.startButton.setDisable(false);
    }

    private void pauseSimulation() {
        this.simulator.stop();

        this.pauseButton.setDisable(true);
        this.startButton.setDisable(false);
    }

    private void playSimulation() {
        this.simulator.start();

        this.pauseButton.setDisable(false);
        this.startButton.setDisable(true);
    }
}
