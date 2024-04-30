package org.vut.ija_project.ApplicationLayer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.vut.ija_project.BusinessLayer.EnvironmentManager;
import org.vut.ija_project.DataLayer.Environment.Environment;
import org.vut.ija_project.DataLayer.Environment.Room;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        Environment room = Room.create(9, 14);
        EnvironmentManager manager = new EnvironmentManager(room);
        MainView mainView = new MainView(manager);

        Scene scene = new Scene(mainView, 1500, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Throwable t) {
            if (t.getCause() != null) {
                t.getCause().printStackTrace();
            } else {
                t.printStackTrace();
            }
        }
    }
}
