package org.vut.ija_project.ApplicationLayer;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.image.Image;

import org.vut.ija_project.BusinessLayer.EnvironmentManager;
import org.vut.ija_project.DataLayer.Common.Position;
import org.vut.ija_project.DataLayer.Obstacle.Obstacle;
import org.vut.ija_project.DataLayer.Robot.Robot;

public class CanvasView extends HBox {
    private Affine affine;
    private Canvas canvas;
    private int canvasWidth = 900;
    private int canvasHeight = 600;
    private GraphicsContext gc;
    private EnvironmentManager environmentManager;
    private Image robotImage;
    private Image obstacleImage;
    private int previousRow;
    private int previousCol;

    public CanvasView(EnvironmentManager environmentManager, MainView mainView) {
        robotImage = new Image(getClass().getResourceAsStream("/insect.png"));
        obstacleImage = new Image(getClass().getResourceAsStream("/bush.png"));

        this.environmentManager = environmentManager;
        this.setSpacing(10);
        // Make canvas
        canvas = new Canvas(canvasWidth, canvasHeight);
        canvas.setOnMouseMoved(this::handleMove);
        canvas.setOnMouseEntered(this::handleEntering);
        canvas.setOnMouseExited(this::handleExit);

        gc = canvas.getGraphicsContext2D();
        eraseObjects();

        this.getChildren().add(canvas);
        this.setAlignment(Pos.CENTER);
    }

    private void handleExit(MouseEvent mouseEvent) {
        update();
    }

    private void handleEntering(MouseEvent mouseEvent) {
        double cellWidth = canvasWidth / (double) environmentManager.getCols();
        double cellHeight = canvasHeight / (double) environmentManager.getRows();

        previousCol = (int) (mouseEvent.getX() / cellWidth);
        previousRow = (int) (mouseEvent.getY() / cellHeight);

        System.out.println("Mouse entered");
    }

    private void handleMove(MouseEvent mouseEvent) {
        double cellWidth = canvasWidth / (double) environmentManager.getCols();
        double cellHeight = canvasHeight / (double) environmentManager.getRows();

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
        for (int x = 0; x <= canvasWidth; x += canvasWidth / environmentManager.getCols()) {
            gc.strokeLine(x, 0, x, canvasHeight);
        }

        for (int y = 0; y <= canvasHeight; y += canvasHeight / environmentManager.getRows()) {
            gc.strokeLine(0, y, canvasWidth, y);
        }
    }

    protected void update() {
        var robots = environmentManager.getRobots();
        var obstacles = environmentManager.getObstacles();
        eraseObjects();

        for (var robot : robots) {
            drawRobot(robot);
        }

        for (var obstacle : obstacles) {
            drawObstacle(obstacle);
        }
    }

    private void drawObstacle(Obstacle obstacle) {
        Position obstaclePos = obstacle.getPosition();
        double cellWidth = canvasWidth / (double) environmentManager.getCols();
        double cellHeight = canvasHeight / (double) environmentManager.getRows();

        // top-left pixel coordinates for the given obstacle's position
        double pixelX = cellWidth  * obstaclePos.getCol();
        double pixelY = cellHeight * obstaclePos.getRow();

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

    private void drawRobot(Robot robot) {
        Position robotPos = robot.getPosition();

        double cellWidth  = canvasWidth  / (double) environmentManager.getCols();
        double cellHeight = canvasHeight / (double) environmentManager.getRows();
        // Calculate the center of the cell where the image will be placed
        double cellCenterX = (robotPos.getCol() + 0.5) * cellWidth;
        double cellCenterY = (robotPos.getRow() + 0.5) * cellHeight;
        // top-left pixel coordinates for the given robot's position
        double pixelX = cellWidth  * robotPos.getCol();
        double pixelY = cellHeight * robotPos.getRow();

        double imageScaleX = cellWidth / robotImage.getWidth();
        double imageScaleY = cellHeight / robotImage.getHeight();
        double scaleFactor = Math.min(imageScaleY, imageScaleX);

        // Center the image in the cell by offsetting the position
        // by half the difference of the cell size and the image size (after scaling)
        double scaledImageWidth = robotImage.getWidth() * scaleFactor;
        double scaledImageHeight = robotImage.getHeight() * scaleFactor;
        double offsetX = (cellWidth - scaledImageWidth) / 2;
        double offsetY = (cellHeight - scaledImageHeight) / 2;

        // Save context
        gc.save();
        // Translate the context to the center of the image for rotation
        gc.translate(cellCenterX, cellCenterY);
        // Rotate context
        gc.rotate(robot.angle());
        // Translate back from the center
        gc.translate(-cellCenterX, -cellCenterY);
        // Draw the image with the transformation applied
        gc.drawImage(robotImage, pixelX + offsetX, pixelY + offsetY, scaledImageWidth, scaledImageHeight);
        // Restore context
        gc.restore();
    }
}
