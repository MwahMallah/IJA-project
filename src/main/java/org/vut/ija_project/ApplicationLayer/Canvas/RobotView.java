package org.vut.ija_project.ApplicationLayer.Canvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.vut.ija_project.BusinessLayer.EnvironmentManager;
import org.vut.ija_project.DataLayer.Common.Position;
import org.vut.ija_project.DataLayer.Robot.Robot;
import org.vut.ija_project.DataLayer.Robot.RobotType;

public class RobotView implements SelectableView {
    private final Robot robot;
    private final CanvasView canvasView;
    private double scaledImageWidth;
    private double scaledImageHeight;
    private GraphicsContext gc;
    private final EnvironmentManager environmentManager;
    private Image robotImage;
    private double currPixelX;
    private double currPixelY;
    private boolean selected;

    public RobotView(Robot robot, CanvasView canvasView, EnvironmentManager environmentManager) {
        this.robot = robot;
        this.canvasView = canvasView;
        this.gc = this.canvasView.getContext();
        this.environmentManager = environmentManager;
        this.selected = false;

        if (robot.getType() == RobotType.AUTONOMOUS) {
            robotImage = new Image(getClass().getResourceAsStream("/bug_red.png"));
        } else if (robot.getType() == RobotType.CONTROLLABLE) {
            robotImage = new Image(getClass().getResourceAsStream("/bug_yellow.png"));
        }

        getImageSize();
        update();
    }

    public void update() {
        Position robotPos = robot.getPosition();

        getImageSize();
        double cellWidth  = this.canvasView.getCellWidth();
        double cellHeight = this.canvasView.getCellHeight();

        // Calculate the center of the cell where the image will be placed
        double cellCenterX = (robotPos.getX() + 0.5) * cellWidth;
        double cellCenterY = (robotPos.getY() + 0.5) * cellHeight;
        // top-left pixel coordinates for the given robot's position
        currPixelX = cellWidth  * robotPos.getX();
        currPixelY = cellHeight * robotPos.getY();

        // Center the image in the cell by offsetting the position
        // by half the difference of the cell size and the image size (after scaling)
        double offsetX = (cellWidth - scaledImageWidth) / 2;
        double offsetY = (cellHeight - scaledImageHeight) / 2;

        if (selected) {
            DropShadow glow = new DropShadow();
            if (robot.getType() == RobotType.AUTONOMOUS) {
                glow.setColor(Color.RED);
            } else if (robot.getType() == RobotType.CONTROLLABLE) {
                glow.setColor(Color.YELLOW);
            }

            glow.setRadius(15);
            glow.setSpread(0.1);

            // Set the glow effect
            gc.setEffect(glow);
        }

        // Save context
        this.gc.save();
        // Translate the context to the center of the image for rotation
        this.gc.translate(cellCenterX, cellCenterY);
        // Rotate context
        this.gc.rotate(robot.angle());
        // Translate back from the center
        this.gc.translate(-cellCenterX, -cellCenterY);
        // Draw the image with the transformation applied
        this.gc.drawImage(robotImage, currPixelX + offsetX, currPixelY + offsetY, scaledImageWidth, scaledImageHeight);
        // Restore context
        this.gc.restore();

        this.gc.setEffect(null);
    }

    private void getImageSize() {
        double cellWidth  = this.canvasView.getCellWidth();
        double cellHeight = this.canvasView.getCellHeight();

        double imageScaleX = cellWidth / robotImage.getWidth();
        double imageScaleY = cellHeight / robotImage.getHeight();
        double scaleFactor = Math.min(imageScaleY, imageScaleX);

        scaledImageWidth = robotImage.getWidth() * scaleFactor;
        scaledImageHeight = robotImage.getHeight() * scaleFactor;
    }

    @Override
    public boolean isClicked(double x, double y) {
        boolean withinHorizontalBoundary =
                x >= currPixelX && x <= (currPixelX + scaledImageWidth);
        boolean withinVerticalBoundary =
                y >= currPixelY && y <= (currPixelY + scaledImageHeight);

        return  withinHorizontalBoundary && withinVerticalBoundary;
    }

    @Override
    public void setSelected(boolean isSelected) {this.selected = isSelected;}

    public Robot getRobot() {
        return this.robot;
    }
}
