package org.vut.ija_project.BusinessLayer;

import org.vut.ija_project.ApplicationLayer.MainView;
import org.vut.ija_project.Common.ObjectConfiguration;
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

    public void addRobot(double y, double x, String type) {
        Position newRobotPos = new Position(y, x);
        Robot newRobot = null;

        if (type.equals("Autonomous")) {
            newRobot = AutonomousRobot.create(currEnvironment, newRobotPos);
        } else if (type.equals("Controllable")) {
            newRobot = ControlledRobot.create(currEnvironment, newRobotPos);
        }

        if (newRobot == null) {
            System.out.println("Robot is not created");
            throw new RuntimeException("Invalid position");
        }

        //if we want to add robot, make current environment position initial
        this.intitialEnvironment = this.currEnvironment.copy();
        mainView.addRobot(newRobot);
    }

    public void addObstacle(double y, double x) {
        Obstacle created = this.currEnvironment.createObstacleAt(y, x);

        if (created == null) {
            System.out.println("Obstacle is not created");
            throw new RuntimeException("Invalid position");
        }

        //if we want to add obstacle, make current environment position initial
        this.intitialEnvironment = this.currEnvironment.copy();
        mainView.addObstacle(created);
    }

    public void simulationReset() {
        this.currEnvironment = this.intitialEnvironment.copy();
        mainView.reset();
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

    public double getHeight() {
        return this.currEnvironment.height();
    }

    public double getWidth() {
        return this.currEnvironment.width();
    }

    public List<Obstacle> getObstacles() {return this.currEnvironment.obstacles();}

    public void deleteRobot(Robot robot) {
        this.currEnvironment.removeRobot(robot);

        this.intitialEnvironment = this.currEnvironment.copy();
        mainView.deleteRobot(robot);
    }

    public void updateRobot(Robot robot, ObjectConfiguration configuration) {
        robot.setConfiguration(configuration);

        this.intitialEnvironment = this.currEnvironment.copy();
        mainView.update();
    }

    public void deleteObstacle(Obstacle obstacle) {
        this.currEnvironment.removeObstacle(obstacle);

        this.intitialEnvironment = this.currEnvironment.copy();
        mainView.deleteObstacle(obstacle);
    }

    public void updateObstacle(Obstacle obstacle, ObjectConfiguration configuration) {
        obstacle.setConfiguration(configuration);

        this.intitialEnvironment = this.currEnvironment.copy();
        mainView.update();
    }

    public void turnControlledRobotCounterClockwise(Robot robot) {
        ControlledRobot controlledRobot = (ControlledRobot) robot;
        controlledRobot.setState(ControlledRobot.State.TURN_COUNTERCLOCKWISE);
    }

    public void turnControlledRobotClockwise(Robot robot) {
        ControlledRobot controlledRobot = (ControlledRobot) robot;
        controlledRobot.setState(ControlledRobot.State.TURN_CLOCKWISE);
    }

    public void moveControlledRobotForward(Robot robot) {
        ControlledRobot controlledRobot = (ControlledRobot) robot;
        controlledRobot.setState(ControlledRobot.State.MOVE_FORWARD);
    }
}
