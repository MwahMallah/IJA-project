import org.junit.Assert;
import org.vut.ija_project.Common.Position;
import org.vut.ija_project.Environment.Environment;
import org.vut.ija_project.Environment.Room;
import org.vut.ija_project.Robot.ControlledRobot;
import org.vut.ija_project.Robot.Robot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

public class RoomTest
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
    public void room_GetRobots() {
        List<Robot> robots = room.robots();
        Assert.assertEquals("There is 2 robots", 2, robots.size());
        robots.remove(0);
        Assert.assertEquals("There is 2 robots", 2, room.robots().size());
    }

    @org.junit.Test
    public void room_AddRobot() {
        Position newPos = new Position(4, 5);
        Robot newRobot = ControlledRobot.create(this.room, newPos);

        List<Robot> robots = room.robots();
        Assert.assertEquals("There is 3 robots now", 3, robots.size());
    }

}