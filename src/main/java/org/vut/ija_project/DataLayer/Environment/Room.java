package org.vut.ija_project.DataLayer.Environment;

import org.vut.ija_project.DataLayer.Common.Observable;
import org.vut.ija_project.DataLayer.Common.Position;
import org.vut.ija_project.DataLayer.Obstacle.Obstacle;
import org.vut.ija_project.DataLayer.Robot.Robot;

import java.util.ArrayList;
import java.util.List;

public class Room implements Environment {
    private int rows;
    private int cols;
    private List<Robot> robotsInRoom;
    private List<Obstacle> obstaclesInRoom;

    private Room(int rows, int cols) {
        this.rows       = rows;
        this.cols       = cols;
        robotsInRoom    = new ArrayList<Robot>();
        obstaclesInRoom = new ArrayList<Obstacle>();
    }

    public static Room create(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Number of rows and cols must be greater than zero.");
        }
        return new Room(rows, cols);
    }

    @Override
    public boolean addRobot(Robot robot) {
        Position robotPos = robot.getPosition();
        if (!containsPosition(robotPos) || obstacleAt(robotPos) || robotAt(robotPos)){
            return false;
        }

        robotsInRoom.add(robot);
        return true;
    }

    @Override
    public boolean containsPosition(Position pos) {
        return pos.getCol() < cols && pos.getRow() < rows && pos.getCol() >= 0 && pos.getRow() >= 0;
    }

    @Override
    public boolean createObstacleAt(int row, int col) {
        Position targetPos = new Position(row, col);
        if (!containsPosition(targetPos) || obstacleAt(targetPos) || robotAt(targetPos)) {
            return false;
        }
        obstaclesInRoom.add(new Obstacle(this, targetPos));
        return true;
    }

    @Override
    public boolean obstacleAt(int row, int col) {
        return obstacleAt(new Position(row, col));
    }

    @Override
    public boolean obstacleAt(Position pos) {
        if (!containsPosition(pos)) return false;

        for (Obstacle obstacle : obstaclesInRoom) {
            if (obstacle.getPosition().equals(pos)) return true;
        }

        return false;
    }

    @Override
    public boolean robotAt(Position pos) {
        if (!containsPosition(pos)) return false;

        for (Robot robot : robotsInRoom) {
            if (robot.getPosition().equals(pos)) return true;
        }

        return false;
    }

    @Override
    public int rows() {
        return rows;
    }

    @Override
    public int cols() {
        return cols;
    }

    @Override
    public List<Robot> robots() {
        return new ArrayList<Robot>(robotsInRoom);
    }

    @Override
    public void update(Observable observable) {
        
    }
}
