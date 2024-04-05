import javafx.geometry.Pos;
import org.vut.ija_project.Common.Position;
import org.vut.ija_project.Environment.Environment;
import org.vut.ija_project.Environment.Room;
import org.vut.ija_project.Robot.ControlledRobot;
import org.vut.ija_project.Robot.Robot;

import org.junit.Test;
import org.junit.Assert;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.vut.ija_project.Robot.ControlledRobot.State.MOVE_FORWARD;

public class ControlledRobotTest
{
    private Environment room;
    private Robot r1, r2;
    /**
     * Creates room, where all tests, are played.
     */
    @org.junit.Before
    public void setUp() {
        // For controlledRobot_Moves
        Environment room = Room.create(5, 8);

        room.createObstacleAt(1, 2);
        room.createObstacleAt(1, 4);
        room.createObstacleAt(1, 5);
        room.createObstacleAt(2, 5);

        Position p1 = new Position(4,2);
        r1 = ControlledRobot.create(room, p1);
        Position p2 = new Position(4,4);
        r2 = ControlledRobot.create(room, p2);

        this.room = room;

    }

    @org.junit.Test
    public void controlledRobot_Moves() {
        // Test for moving robot in the environment with obstacles

        assertTrue(r1.canMove());
        assertTrue(r2.canMove());

        Position p3 = new Position(3,4);

        // Set state directly in the test method
        ((ControlledRobot) r2).setState(ControlledRobot.State.MOVE_FORWARD);
        String strMove = "MOVE_FORWARD";
        r2.updatePosition(strMove);
        assertEquals(p3, r2.getPosition());

        ((ControlledRobot) r2).setState(ControlledRobot.State.TURN_CLOCKWISE);
        String strTurn1 = "TURN_CLOCKWISE";
        r2.updatePosition(strTurn1);
        r2.updatePosition(strTurn1);
        ((ControlledRobot) r2).setState(ControlledRobot.State.MOVE_FORWARD);
        r2.updatePosition(strMove);
        r2.updatePosition(strMove);
        r2.updatePosition(strMove);
        Position p6 = new Position(3,7);
        assertEquals(p6, r2.getPosition());

        // Set state directly in the test method
        ((ControlledRobot) r1).setState(ControlledRobot.State.TURN_CLOCKWISE);
        String strTurn = "TURN_CLOCKWISE";
        r1.updatePosition(strTurn);
        r1.updatePosition(strTurn);
        assertEquals(90, r1.angle()); // control if robot is turned by 45 degrees

        ((ControlledRobot) r1).setState(ControlledRobot.State.MOVE_FORWARD);
        r1.updatePosition(strMove);
        Position p5 = new Position(4,3);
        assertEquals(p5, r1.getPosition());

        ((ControlledRobot) r1).setState(ControlledRobot.State.MOVE_FORWARD);
        r1.updatePosition(strMove);
        Position p7 = new Position(4,4);
        assertEquals(p7, r1.getPosition());

        ((ControlledRobot) r1).setState(ControlledRobot.State.MOVE_FORWARD);
        r1.updatePosition(strMove);
        Position p8 = new Position(4,5);
        assertEquals(p8, r1.getPosition());

    }


    @org.junit.Test
    public void controlledRobot_ChecksObstacle() {

        // For controlledRobot_ChecksObstacle
        Environment room1 = Room.create(6, 6);

        // Test for checking obstacles in the environment
        Position p1 = new Position(1, 2);
        Position p2 = new Position(1, 4);
        Position p3 = new Position(2, 4);
        Position p4 = new Position(2, 5);

        room1.createObstacleAt(1, 2);
        room1.createObstacleAt(1, 4);

        assertTrue(room1.obstacleAt(p1));
        assertTrue(room1.obstacleAt(p2));

        Robot r1 = ControlledRobot.create(room1, p1);
        Assert.assertNull(r1); // robot cannot be created at position with obstacle

        Robot r2 = ControlledRobot.create(room1, p3);
        Assert.assertFalse("We have obstacle on this position",r2.canMove());

        Robot r3 = ControlledRobot.create(room1, p4);
        Assert.assertNotNull(r3); // robot can be created at position without obstacle

        Robot r4 = ControlledRobot.create(room1, p4);
        Assert.assertNull(r4); // robot can be created at position, where is already another robot

    }

}
