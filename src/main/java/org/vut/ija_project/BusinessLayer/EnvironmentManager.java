package org.vut.ija_project.BusinessLayer;

import org.vut.ija_project.ApplicationLayer.MainView;
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
    private MainView mainView;
    private Environment currEnvironment;
    private Environment intitialEnvironment;

    public EnvironmentManager(Environment environment) {
        this.intitialEnvironment = environment;
        this.currEnvironment = this.intitialEnvironment.copy();
    }

    public void addMainView(MainView mainView) {
        this.mainView = mainView;
    }

    public void addRobot(int row, int col, String type) {
        Position newRobotPos = new Position(row, col);
        Robot newRobot = null;

        if (type.equals("Automated")) {
            newRobot = AutonomousRobot.create(currEnvironment, newRobotPos);
        } else if (type.equals("Controlled")) {
            newRobot = ControlledRobot.create(currEnvironment, newRobotPos);
        }

        if (newRobot == null) {
            System.out.println("Robot is not created");
            throw new RuntimeException("Invalid position");
        }

        //if we want to add robot, make current environment position initial
        this.intitialEnvironment = this.currEnvironment.copy();

        mainView.update();
    }

    public void addObstacle(int row, int col) {
        boolean created = this.currEnvironment.createObstacleAt(row, col);

        if (!created) {
            System.out.println("Obstacle is not created");
            throw new RuntimeException("Invalid position");
        }

        //if we want to add obstacle, make current environment position initial
        this.intitialEnvironment = this.currEnvironment.copy();

        mainView.update();
    }

    public void simulationReset() {
        this.currEnvironment = this.intitialEnvironment.copy();
        mainView.update();
    }

    public void simulationStep() {
        List<Robot> robots = this.currEnvironment.robots();


        for (var robot : robots) {
            robot.updatePosition();
        }

        mainView.update();
    }

    public Simulator getSimulator() {
        return new Simulator(this);
    }

    public List<Robot> getRobots() {
        return this.currEnvironment.robots();
    }

    public int getRows() {
        return this.currEnvironment.rows();
    }

    public int getCols() {
        return  this.currEnvironment.cols();
    }

    public List<Obstacle> getObstacles() {return this.currEnvironment.obstacles();}
}
