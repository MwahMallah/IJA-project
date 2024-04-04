package org.vut.ija_project.Environment;

import org.vut.ija_project.Common.Position;
import org.vut.ija_project.Common.Observer;
import org.vut.ija_project.Robot.Robot;

import java.util.List;

public interface Environment extends Observer {
    /*
     * Adds robot to its position
     * @return success of operation
     */
    public boolean addRobot(Robot robot);

    /*
     * Checks if position is inside Environment
     * @return success of operation
     */
    public boolean containsPosition(Position pos);

    /*
     * Creates obstacle at given position
     * @return success of operation
     */
    public boolean createObstacleAt(int row, int col);

    /*
     * Checks if there is obstacle at this position
     * @return success of operation
     */
    public boolean obstacleAt(int row, int col);

    /*
     * Checks if there is obstacle at this position
     * @return success of operation
     */
    public boolean obstacleAt(Position pos);

    /*
     * Checks if there is robot this at position
     * @return success of operation
     */
    public boolean robotAt(Position pos);

    int rows();

    int cols();

    List<Robot> robots();
}