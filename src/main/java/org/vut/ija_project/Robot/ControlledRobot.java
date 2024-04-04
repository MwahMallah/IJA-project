package org.vut.ija_project.Robot;

import org.vut.ija_project.Common.Observer;
import org.vut.ija_project.Common.Position;
import org.vut.ija_project.Environment.Environment;

import java.util.ArrayList;
import java.util.List;

public class ControlledRobot implements Robot {
    List<Observer> observers;
    private final Environment env;
    private Position pos;
    private int angle;
    private State currState;

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
        notifyObservers();
    }

    private void turnCounterClockwise() {
        angle = (angle - 45 + 360) % 360;
        notifyObservers();
    }

    @Override
    public boolean canMove() {
        Position targetPos = getTargetPosition();
        return env.containsPosition(targetPos) && !env.obstacleAt(targetPos) && !env.robotAt(targetPos);
    }

    private Position getTargetPosition() {
        // trigonometric angle is rotating counter-clockwise => multiply by -1
        // angle 0 is angle 90 on trigonometric circular => add 90
        double angleRadians = Math.toRadians(-angle + 90);
        int deltaX = (int) Math.round(Math.cos(angleRadians));
        int deltaY = (int) Math.round(Math.sin(angleRadians)) * (-1);

        return new Position(deltaY + pos.getRow(), deltaX + pos.getCol());
    }

    @Override
    public Position getPosition() {
        return pos;
    }

    @Override
    public void updatePosition() {
        switch (currState) {
            case MOVE_FORWARD:
                if (!move()) currState = State.NOTHING;
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

    public void setState(State state) {
        currState = state;
    }

    private boolean move() {
        if (!canMove()) return false;

        pos = getTargetPosition();
        notifyObservers();
        return true;
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
