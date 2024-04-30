package org.vut.ija_project.DataLayer.Obstacle;

import org.vut.ija_project.DataLayer.Common.Position;
import org.vut.ija_project.DataLayer.Environment.Environment;

public class Obstacle {
    private Environment env;
    private Position pos;
    private final double obstacleSize = 0.8;

    public Obstacle(Environment env, Position pos) {
        this.pos = pos;
        this.env = env;
    }

    public Position getPosition() {
        return pos;
    }

    public double getObstacleSize() {return obstacleSize;}
}