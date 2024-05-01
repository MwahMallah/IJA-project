package org.vut.ija_project.DataLayer.Robot;

import org.vut.ija_project.Common.ObjectConfiguration;
import org.vut.ija_project.DataLayer.Common.Observer;
import org.vut.ija_project.DataLayer.Common.Position;
import org.vut.ija_project.DataLayer.Environment.Environment;

import java.util.ArrayList;
import java.util.List;

public class ControlledRobot implements Robot {
    private double velocity;
    List<Observer> observers;
    private final Environment env;
    private Position pos;
    private int angle;
    private State currState;
    private double robotSize;

    public enum State {
        TURN_COUNTERCLOCKWISE,
        TURN_CLOCKWISE,
        MOVE_FORWARD,
        NOTHING
    }

    private ControlledRobot(Environment env, Position pos) {
        observers = new ArrayList<Observer>();
        this.env = env;
        this.pos = pos;
        this.angle = 0;
        this.robotSize = 1;
        this.velocity = 0.1;
        addObserver(env);
        currState = State.NOTHING;
    }

    public static ControlledRobot create(Environment env, Position pos) {
        if (env == null || pos == null) return null;
        if (env.obstacleAt(pos) || env.robotAt(pos)) return null;

        ControlledRobot newRobot = new ControlledRobot(env, pos);
        env.addRobot(newRobot);
        return newRobot;
    }

    @Override
    public int angle() {
        return angle;
    }

    private void turnClockWise() {
        angle = (angle + 45) % 360;
        currState = State.NOTHING;
        notifyObservers();
    }

    private void turnCounterClockwise() {
        angle = (angle - 45 + 360) % 360;
        currState = State.NOTHING;
        notifyObservers();
    }

    @Override
    public boolean canMove() {
        Position targetPos = getTargetPosition();
        return env.containsPosition(targetPos) && !env.obstacleAt(targetPos) && !env.robotAt(targetPos, this);
    }

    private Position getTargetPosition() {
        // trigonometric angle is rotating counter-clockwise => multiply by -1
        // angle 0 is angle 90 on trigonometric circular => add 90
        double angleRadians = Math.toRadians(-angle + 90);
        double deltaX = Math.cos(angleRadians) * this.velocity;
        double deltaY = Math.sin(angleRadians) * (-1) * this.velocity;

        return new Position(deltaY + pos.getY(), deltaX + pos.getX());
    }

    @Override
    public void updatePosition() {
        switch (currState) {
            case MOVE_FORWARD:
                move();
                break;
            case TURN_COUNTERCLOCKWISE:
                turnCounterClockwise();
                break;
            case TURN_CLOCKWISE:
                turnClockWise();
                break;
            case NOTHING:
                break;
        }
    }

    @Override
    public Robot copy(Environment env) {
        return new ControlledRobot(env, this.pos);
    }

    //TODO: change velocity
    @Override
    public double getVelocity() {
        return this.velocity;
    }

    @Override
    public double getRotationAngle() {
        return 0;
    }

    @Override
    public void setConfiguration(ObjectConfiguration configuration) {
        this.pos = new Position(configuration.newY, configuration.newX);
        this.angle = configuration.newAngle;
        this.velocity = configuration.newVelocity;
    }
    @Override
    public Position getPosition() {
        return pos;
    }
    @Override
    public double getRobotSize() {return this.robotSize;}

    @Override
    public RobotType getType() {return RobotType.CONTROLLABLE;}

    public void setState(State state) {
        currState = state;
    }

    private void move() {
        currState = State.NOTHING;
        if (!canMove()) {return;}

        pos = getTargetPosition();
        currState = State.NOTHING;
        notifyObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(this);
        }
    }

}
