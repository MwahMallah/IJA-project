package org.vut.ija_project.BusinessLayer;

import org.vut.ija_project.DataLayer.Common.Position;
import org.vut.ija_project.DataLayer.Environment.Environment;
import org.vut.ija_project.DataLayer.Obstacle.Obstacle;
import org.vut.ija_project.DataLayer.Robot.AutonomousRobot;
import org.vut.ija_project.DataLayer.Robot.ControlledRobot;
import org.vut.ija_project.DataLayer.Robot.Robot;

import java.util.List;

/**
 * The main controller for the application.
 * This class manages the interactions between the user interface
 * and the environment in the application.
 */
public class EnvironmentManager
{
    private final Environment environment;

    public EnvironmentManager(Environment environment) {
        this.environment = environment;
    }

    public void AddRobot(int row, int col, String type) {
        Position newRobotPos = new Position(row, col);
        Robot newRobot = null;

        if (type.equals("Automated")) {
            newRobot = AutonomousRobot.create(environment, newRobotPos);
        } else if (type.equals("Controlled")) {
            newRobot = ControlledRobot.create(environment, newRobotPos);
        }

        if (newRobot == null) {
            System.out.println("here");
            throw new RuntimeException("Invalid position");
        }
    }

    public void AddObstacle(Obstacle obstacle) {

    }

    public void SimulationStep() {
        List<Robot> robots = this.environment.robots();

        for (var robot : robots) {
            robot.updatePosition();
        }
    }

    public List<Robot> GetRobots() {
        return this.environment.robots();
    }

    public int getRows() {
        return this.environment.rows();
    }

    public int getCols() {
        return  this.environment.cols();
    }
}
