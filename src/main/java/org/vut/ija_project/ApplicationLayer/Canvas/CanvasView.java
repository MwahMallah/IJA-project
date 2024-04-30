package org.vut.ija_project.ApplicationLayer.Canvas;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import org.vut.ija_project.ApplicationLayer.MainView;
import org.vut.ija_project.ApplicationLayer.SelectedView.SelectedView;
import org.vut.ija_project.ApplicationLayer.SelectedView.SelectedViewFactory;
import org.vut.ija_project.BusinessLayer.EnvironmentManager;
import org.vut.ija_project.DataLayer.Common.Position;
import org.vut.ija_project.DataLayer.Obstacle.Obstacle;
import org.vut.ija_project.DataLayer.Robot.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CanvasView extends HBox {
    private final MainView mainView;
    private Canvas canvas;
    protected int canvasWidth = 900;
    protected int canvasHeight = 600;
    private GraphicsContext gc;
    private EnvironmentManager environmentManager;
    private Image obstacleImage;
    private int previousRow;
    private int previousCol;
    private double cellWidth;
    private double cellHeight;
    private List<RobotView> robotViewList;

    public CanvasView(EnvironmentManager environmentManager, MainView mainView) {
        robotViewList = new ArrayList<RobotView>();
        obstacleImage = new Image(getClass().getResourceAsStream("/bush.png"));

        this.environmentManager = environmentManager;
        this.mainView = mainView;
        this.setSpacing(10);
        // Make canvas
        canvas = new Canvas(canvasWidth, canvasHeight);
        this.cellWidth = canvasWidth / (environmentManager.getWidth() + 1);
        this.cellHeight = canvasHeight / (environmentManager.getHeight() + 1);

        canvas.setOnMouseMoved(this::handleMove);
        canvas.setOnMouseEntered(this::handleEntering);
        canvas.setOnMouseExited(this::handleExit);
        canvas.setOnMouseClicked(this::handleClick);

        gc = canvas.getGraphicsContext2D();
        eraseObjects();

        this.getChildren().add(canvas);
        this.setAlignment(Pos.CENTER);
    }

    private void handleClick(MouseEvent mouseEvent) {
        SelectedView selectedView = null;
        RobotView selectedRobotView = null;

        robotViewList.forEach(robotView -> {robotView.setSelected(false);});
        for (var robotView : robotViewList) {
            if (robotView.isClicked(mouseEvent.getX(), mouseEvent.getY())) {
                selectedRobotView = robotView; break;
            }
        }

        if (selectedRobotView != null) {
            selectedRobotView.setSelected(true);
            selectedView = SelectedViewFactory.create(selectedRobotView, environmentManager);
        }

        mainView.setSelected(selectedView);
    }

    private void handleExit(MouseEvent mouseEvent) {
        update();
    }

    private void handleEntering(MouseEvent mouseEvent) {
        previousCol = (int) (mouseEvent.getX() / cellWidth);
        previousRow = (int) (mouseEvent.getY() / cellHeight);
    }

    private void handleMove(MouseEvent mouseEvent) {
        int currCol = (int) (mouseEvent.getX() / cellWidth);
        int currRow = (int) (mouseEvent.getY() / cellHeight);

        if (currCol == previousCol && currRow == previousRow) return;

        // Redraw the canvas to clear the previous highlight
        update();

        DropShadow glow = new DropShadow();
        glow.setColor(Color.YELLOW);
        glow.setRadius(10); // Adjust the radius for the desired glow size
        glow.setSpread(0.05); // How much the glow spreads from its source, value from 0 to 1

        // Set the glow effect
        gc.setEffect(glow);

        // Set a semi-transparent fill color
        gc.setFill(Color.rgb(255, 255, 0, 0.1)); // Adjust transparency as needed

        gc.fillRect(cellWidth * currCol, cellHeight * currRow, cellWidth, cellHeight);
        gc.setEffect(null);
    }

    private void eraseObjects() {
        gc.setFill(Color.LIGHTGRAY);

        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setLineWidth(0.2);
        for (int x = 0; x <= environmentManager.getWidth() + 1; x++) {
            double xPos = x * cellWidth;
            gc.strokeLine(xPos, 0, xPos, canvasHeight);
        }

        // Draw horizontal lines (rows)
        for (int y = 0; y <= environmentManager.getHeight() + 1; y++) {
            double yPos = y * cellHeight;
            gc.strokeLine(0, yPos, canvasWidth, yPos);
        }
    }

    public void update() {
        var obstacles = environmentManager.getObstacles();
        eraseObjects();

        for (var robotView : robotViewList) {
            robotView.update();
        }

        for (var obstacle : obstacles) {
            drawObstacle(obstacle);
        }
    }

    private void drawObstacle(Obstacle obstacle) {
        Position obstaclePos = obstacle.getPosition();

        // top-left pixel coordinates for the given obstacle's position
        double pixelX = cellWidth  * obstaclePos.getX();
        double pixelY = cellHeight * obstaclePos.getY();

        double imageScaleX = cellWidth / obstacleImage.getWidth();
        double imageScaleY = cellHeight / obstacleImage.getHeight();
        double scaleFactor = Math.min(imageScaleY, imageScaleX);

        // Center the image in the cell by offsetting the position
        // by half the difference of the cell size and the image size (after scaling)
        double scaledImageWidth = obstacleImage.getWidth() * scaleFactor;
        double scaledImageHeight = obstacleImage.getHeight() * scaleFactor;
        double offsetX = (cellWidth - scaledImageWidth) / 2;
        double offsetY = (cellHeight - scaledImageHeight) / 2;

        gc.drawImage(obstacleImage, pixelX + offsetX, pixelY + offsetY, scaledImageWidth, scaledImageHeight);
    }

    public void addRobot(Robot robot) {
        var newRobotView = new RobotView(robot, this, environmentManager);
        robotViewList.add(newRobotView);
        update();
    }

    public void removeRobot(Robot robot) {
        Optional<RobotView> robotViewToRemoveOptional = robotViewList.stream()
                .filter(r -> r.getRobot() == robot)
                .findFirst();

        if (robotViewToRemoveOptional.isPresent()) {
            RobotView robotViewToRemove = robotViewToRemoveOptional.get();
            robotViewList.remove(robotViewToRemove);
        } else {
            System.out.println("Can't be here (I hope)");
        }
        update();
    }

    public void reset() {
        robotViewList = new ArrayList<RobotView>();
        List<Robot> robots = this.environmentManager.getRobots();

        for (var robot : robots) {
            addRobot(robot);
        }
    }

    public GraphicsContext getContext() {
        return gc;
    }

    public double getCellWidth() {return cellWidth;}
    public double getCellHeight() {return cellHeight;}
}
