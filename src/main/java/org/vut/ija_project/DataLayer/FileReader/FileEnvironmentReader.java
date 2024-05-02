package org.vut.ija_project.DataLayer.FileReader;

import org.vut.ija_project.DataLayer.Common.Position;
import org.vut.ija_project.DataLayer.Robot.ControlledRobot;
import org.vut.ija_project.DataLayer.Environment.Environment;
import java.io.*;
import java.util.Objects;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.vut.ija_project.DataLayer.Environment.Room;
import org.vut.ija_project.DataLayer.Robot.AutonomousRobot;

public class FileEnvironmentReader
{
    private enum ObjectType {
        OBSTACLE,
        CONTROLLED_ROBOT,
        AUTONOMOUS_ROBOT,
        NOT_FOUND
    }
    private static Environment environment;

    public static Environment createEnvironmentFromSource(File source) throws RuntimeException
    {
        environment = null;

        try (Reader fileReader = new FileReader(source);
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withHeader())) {
            boolean isFirst = true;
            for (CSVRecord record : csvParser) {
                if (isFirst) {
                    if (!Objects.equals(record.get("type"), "e"))
                        throw new RuntimeException("there is no Environment specifier");

                    double x = Double.parseDouble(record.get("x"));
                    double y = Double.parseDouble(record.get("y"));
                    environment = Room.create(x, y);
                    isFirst = false;
                } else {
                    ObjectType type = GetType(record.get("type"));
                    if (type == ObjectType.NOT_FOUND) throw new RuntimeException("Unknown type: " + record.get("type"));
                    double x = Double.parseDouble(record.get("x"));
                    double y = Double.parseDouble(record.get("y"));
                    int angle = Integer.parseInt(record.get("angle"));
                    int angleRotate = Integer.parseInt(record.get("rotate_angle"));

                    addObjectToEnvironment(type, x, y, angle, angleRotate);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return environment;
    }


    private static ObjectType GetType(String type) {
        return switch (type) {
            case "c" -> ObjectType.CONTROLLED_ROBOT;
            case "a" -> ObjectType.AUTONOMOUS_ROBOT;
            case "o" -> ObjectType.OBSTACLE;
            default -> ObjectType.NOT_FOUND;
        };
    }

    private static void addObjectToEnvironment(ObjectType type, double x, double y, int angle, int rotateAngle) {
        switch (type) {
            case OBSTACLE -> environment.createObstacleAt(y, x);
            case CONTROLLED_ROBOT -> ControlledRobot.create(environment, new Position(y, x), angle);
            case AUTONOMOUS_ROBOT -> AutonomousRobot.create(environment, new Position(y, x), angle, rotateAngle);
            default -> {}
        }
    }
}
