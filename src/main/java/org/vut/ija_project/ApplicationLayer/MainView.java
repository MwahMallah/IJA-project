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
import javafx.scene.transform.Affine;

import org.vut.ija_project.BusinessLayer.EnvironmentManager;
import org.vut.ija_project.DataLayer.Common.Position;

public class MainView extends HBox
{
    private EnvironmentManager environmentManager;
    private VBox leftSide;
    private VBox rightSide;
    private CanvasView canvasView;
    private ButtonsView buttonsView;

    public MainView(EnvironmentManager environmentManager) {
        this.environmentManager = environmentManager;
        this.environmentManager.addMainView(this);

        this.leftSide = new FormsView(environmentManager);
        this.rightSide = new VBox(10);

        //width is 20% left and 80% right
        leftSide.maxWidthProperty().bind(this.widthProperty().multiply(1/5f));
        rightSide.maxWidthProperty().bind(this.widthProperty().multiply(4/5f));

        canvasView = new CanvasView(environmentManager, this);
        buttonsView = new ButtonsView(environmentManager);
        this.rightSide.getChildren().addAll(buttonsView, canvasView);

        this.getChildren().addAll(leftSide, rightSide);
        HBox.setHgrow(leftSide, Priority.ALWAYS);
        HBox.setHgrow(rightSide, Priority.ALWAYS);
    }

    public void update() {
        canvasView.update();
    }
}
