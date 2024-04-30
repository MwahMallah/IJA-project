package org.vut.ija_project.ApplicationLayer.SelectedView;

import org.vut.ija_project.ApplicationLayer.Canvas.RobotView;
import org.vut.ija_project.BusinessLayer.EnvironmentManager;

public class SelectedViewFactory {
    public static SelectedViewRobot create(RobotView robotView, EnvironmentManager environmentManager) {
        return new SelectedViewRobot(robotView, environmentManager);
    }
}
