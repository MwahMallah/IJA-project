import org.vut.ija_project.Common.Position;
import org.vut.ija_project.Environment.Environment;
import org.vut.ija_project.Environment.Room;
import org.vut.ija_project.Robot.ControlledRobot;
import org.vut.ija_project.Robot.Robot;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControlledRobotTest
{
    private Environment room;
    private Robot r1, r2;
    /**
     * Creates room, where all tests, are played.
     */
    @org.junit.Before
    public void setUp() {
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

    }


    @org.junit.Test
    public void controlledRobot_ChecksObstacle() {

    }

}
