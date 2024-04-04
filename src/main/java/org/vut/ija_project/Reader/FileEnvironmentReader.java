package org.vut.ija_project.Reader;

import org.vut.ija_project.Common.Position;
import org.vut.ija_project.Environment.Environment;
import java.io.*;
import java.util.Objects;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.vut.ija_project.Environment.Room;
import org.vut.ija_project.Robot.AutonomousRobot;
import org.vut.ija_project.Robot.ControlledRobot;

public class FileEnvironmentReader implements EnvironmentReader
{

    private enum ObjectType {
        OBSTACLE,
        CONTROLLED_ROBOT,
        AUTONOMOUS_ROBOT,
        NOT_FOUND
    }
    Environment environment;

    @Override
    public Environment createEnvironmentFromSource(File source) throws RuntimeException
    {
        environment = null;

        try (Reader fileReader = new FileReader(source))
        {
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);
            CSVRecord firstRecord = null;
            for (CSVRecord record : csvParser) {
                firstRecord = record;
                break; // Break after processing the first record
            }
            if (firstRecord == null || !Objects.equals(firstRecord.get("type"), "e")) throw new RuntimeException("there is no Environment specifier");
            int x = Integer.parseInt(firstRecord.get("x"));
            int y = Integer.parseInt(firstRecord.get("y"));
            environment = Room.create(x, y);

            for (CSVRecord record : csvParser) {
                ObjectType type = GetType(record.get("type"));
                if (type == ObjectType.NOT_FOUND) throw new RuntimeException("Unknown type: " + record.get("type"));
                x = Integer.parseInt(record.get("x"));
                y = Integer.parseInt(record.get("y"));

                addObjectToEnvironment(type, x, y);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return environment;
    }


    private ObjectType GetType(String type) {
        switch (type) {
            case "c":
                return ObjectType.CONTROLLED_ROBOT;
            case "a":
                return ObjectType.AUTONOMOUS_ROBOT;
            case "o":
                return ObjectType.OBSTACLE;
            default:
                return ObjectType.NOT_FOUND;
        }
    }


    private void addObjectToEnvironment(ObjectType type, int x, int y) {
        switch (type) {
            case OBSTACLE -> environment.createObstacleAt(x, y);
            case CONTROLLED_ROBOT -> {
                var controlledRobot = ControlledRobot.create(environment, new Position(x, y));
                environment.addRobot(controlledRobot);
            }
            case AUTONOMOUS_ROBOT -> {
                var autoRobot = AutonomousRobot.create(environment, new Position(x, y));
                environment.addRobot(autoRobot);
            }
            default -> {
            }
        }
    }
}
