package org.vut.ija_project.DataLayer.Robot;

import org.vut.ija_project.DataLayer.Common.Observable;
import org.vut.ija_project.DataLayer.Common.Position;

public interface Robot extends Observable {

    /*
     * @return Current angle of robot.
     */
    public int angle();

    /*
     * Checks if robot can change position by
     * 1 field in front of robot's view.
     */
    public boolean canMove();

    /*
     * @return Current robot's position.
     */
    public Position getPosition();

    /*
     * Updates current robot's position.
     */
    public void updatePosition();


}
