import org.junit.jupiter.api.Test;
import org.junit.Assert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.vut.ija_project.Environment.Environment;
import org.vut.ija_project.Environment.Room;
import org.vut.ija_project.Robot.AutonomousRobot;
import org.vut.ija_project.Robot.Robot;
import org.vut.ija_project.Common.Position;

public class AutonomousRobotTest
{
    private Environment room;
    private Robot r1, r2;

    @org.junit.Before
    public void setUp() {
        Environment room = Room.create(5, 8);

        room.createObstacleAt(1, 2);
        room.createObstacleAt(1, 4);
        room.createObstacleAt(2, 2);

        Position p1 = new Position(4,2);
        r1 = AutonomousRobot.create(room, p1);
        Position p2 = new Position(4,4);
        r2 = AutonomousRobot.create(room, p2);

        this.room = room;
    }

    @org.junit.Test
    public void AutonomousRobot_Turns() {

        String strTurn1 = "CLOCKWISE";
        String strTurn2 = "COUNTERCLOCKWISE";

        // Test for turning robot in the environment without obstacles
        Assert.assertTrue(r1.canMove());
        Assert.assertTrue(r2.canMove());

        Position p3 = new Position(2,4);
        ((AutonomousRobot) r2).setTurningDirection(AutonomousRobot.Direction.CLOCKWISE);
        r2.updatePosition(strTurn1);
        r2.updatePosition(strTurn1);
        Assert.assertEquals(p3, r2.getPosition());

        Position p4 = new Position(2,2);
        ((AutonomousRobot) r1).setTurningDirection(AutonomousRobot.Direction.CLOCKWISE);
        r1.updatePosition(strTurn1);
        r1.updatePosition(strTurn1);
        r1.updatePosition(strTurn1);
        // we have obstacle at (2,2) so robot should not move, we will have position (2,3)
        Assert.assertNotEquals(p4, r1.getPosition());
        Assert.assertEquals(new Position(2,3), r1.getPosition());

        ((AutonomousRobot) r1).setTurningDirection(AutonomousRobot.Direction.COUNTERCLOCKWISE);
        r1.updatePosition(strTurn2);
        r1.updatePosition(strTurn2);
        Position p5 = new Position(1,3);
        Assert.assertEquals(p5, r1.getPosition());

    }

    @org.junit.Test
    public void AutonomousRobot_CheckObstacles() {

        // Test for turning robot in the environment with obstacles
        Position p3 = new Position(1,4);
        Robot r3 = AutonomousRobot.create(room, p3);
        Assert.assertNull("Robot should not be created, we have obstacle", r3);

        Position p4 = new Position(3,3);
        Robot r4 = AutonomousRobot.create(room, p4);
        Assert.assertNotNull("Robot should be created, we do not have obstacle", r4);
        room.createObstacleAt(2, 3);
        room.createObstacleAt(3, 3);
        Assert.assertFalse("We have obstacle on this position", r4.canMove());

        Position p5 = new Position(3,4);
        Robot r5 = AutonomousRobot.create(room, p5);
        Robot r6 = AutonomousRobot.create(room, p5);
        Assert.assertNotEquals("Robot should be created", r5);
        Assert.assertNull("Robot should not be created, we have robot on this position", r6);

        room.createObstacleAt(4, 4);
        room.createObstacleAt(3, 5);
        room.createObstacleAt(2, 4);
        room.createObstacleAt(3, 3);
        Assert.assertFalse("We have obstacles around the robot", r5.canMove());
    }
}
